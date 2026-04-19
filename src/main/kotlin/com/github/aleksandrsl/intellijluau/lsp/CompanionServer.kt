@file:Suppress("UnstableApiUsage")

package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.google.gson.JsonParser
import com.google.gson.Strictness
import com.google.gson.stream.JsonReader
import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.platform.lsp.api.LspServerManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import me.saket.bytesize.megabytes
import org.apache.http.HttpHeaders
import org.apache.http.HttpStatus
import java.io.InputStream
import java.net.InetSocketAddress
import java.util.zip.GZIPInputStream

private val LOG = logger<CompanionServer>()

private val MAX_BODY_SIZE = 3.megabytes

private const val MAX_BODY_LENGTH = 500

class CompanionServer(
    private val project: Project,
    private val port: Int,
) {
    private var server: HttpServer? = null

    fun start() {
        val httpServer = HttpServer.create(InetSocketAddress("127.0.0.1", port), 0)
        httpServer.createContext("/full") { exchange -> handleFull(exchange) }
        httpServer.createContext("/clear") { exchange -> handleClear(exchange) }
        httpServer.createContext("/get-file-paths") { exchange -> handleGetFilePaths(exchange) }
        httpServer.start()
        server = httpServer
        LOG.info("Companion plugin server started on port $port")
    }

    fun stop() {
        server?.stop(0)
        server = null
        LOG.info("Companion plugin server stopped")
    }

    val isRunning: Boolean
        get() = server != null

    private fun handleFull(exchange: HttpExchange) {
        exchange.use {
            if (exchange.requestMethod != "POST") {
                exchange.sendResponse(HttpStatus.SC_METHOD_NOT_ALLOWED, "Method Not Allowed")
                return
            }

            val contentLength = exchange.requestHeaders.getFirst(HttpHeaders.CONTENT_LENGTH)?.toLongOrNull() ?: 0
            if (contentLength > MAX_BODY_SIZE.inWholeBytes) {
                exchange.sendResponse(HttpStatus.SC_REQUEST_TOO_LONG, "Request body too large. Limit: $MAX_BODY_SIZE")
                return
            }

            val body = try {
                val inputStream = decompressIfNeeded(exchange)
                inputStream.bufferedReader().readText()
            } catch (e: Exception) {
                LOG.debug("Failed to read request body", e)
                exchange.sendResponse(HttpStatus.SC_BAD_REQUEST, "Failed to read request body")
                return
            }

            val jsonObject = try {
                val reader = JsonReader(body.reader())
                reader.strictness = Strictness.LENIENT
                JsonParser.parseReader(reader).asJsonObject
            } catch (e: Exception) {
                val preview = if (body.length > MAX_BODY_LENGTH) body.substring(0, MAX_BODY_LENGTH) + "..." else body
                LOG.warn("Failed to parse JSON body (length=${body.length}): $preview", e)
                exchange.sendResponse(HttpStatus.SC_BAD_REQUEST, "Invalid JSON")
                return
            }

            val tree = jsonObject.get("tree")
            if (tree == null) {
                exchange.sendResponse(HttpStatus.SC_BAD_REQUEST, "Missing 'tree' property")
                return
            }

            if (!sendLspNotification { it.pluginFull(tree) }) {
                LOG.debug("LSP server not available, ignoring /full request")
            }
            exchange.sendResponse(HttpStatus.SC_OK, "OK")
        }
    }

    private fun handleClear(exchange: HttpExchange) {
        exchange.use {
            if (exchange.requestMethod != "POST") {
                exchange.sendResponse(HttpStatus.SC_METHOD_NOT_ALLOWED, "Method Not Allowed")
                return
            }

            if (!sendLspNotification { it.pluginClear() }) {
                LOG.debug("LSP server not available, ignoring /clear request")
            }
            exchange.sendResponse(HttpStatus.SC_OK, "OK")
        }
    }

    private fun handleGetFilePaths(exchange: HttpExchange) {
        exchange.use {
            if (exchange.requestMethod != "GET") {
                exchange.sendResponse(HttpStatus.SC_METHOD_NOT_ALLOWED, "Method Not Allowed")
                return
            }

            try {
                val files = runReadAction {
                    FileTypeIndex.getFiles(LuauFileType, GlobalSearchScope.projectScope(project))
                        .map { it.path }
                }
                val json = com.google.gson.JsonObject().apply {
                    val array = com.google.gson.JsonArray()
                    files.forEach { array.add(it) }
                    add("files", array)
                }
                exchange.responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json")
                exchange.sendResponse(HttpStatus.SC_OK, json.toString())
            } catch (e: Exception) {
                LOG.warn("Failed to get file paths", e)
                exchange.sendResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Failed to get file paths")
            }
        }
    }

    private fun decompressIfNeeded(exchange: HttpExchange): InputStream {
        val encoding = exchange.requestHeaders.getFirst(HttpHeaders.CONTENT_ENCODING)
        return if (encoding != null && encoding.equals("gzip", ignoreCase = true)) {
            GZIPInputStream(exchange.requestBody)
        } else {
            exchange.requestBody
        }
    }

    /**
     * Send a notification to the Luau LSP server.
     * Uses reflection to support both old (lsp4jServer, 2024.x) and new (sendNotification, 2025.x+) IntelliJ APIs.
     */
    private fun sendLspNotification(action: (LuauLanguageServer) -> Unit): Boolean {
        val lspServer = LspServerManager.getInstance(project)
            .getServersForProvider(LuauLspServerSupportProvider::class.java)
            .firstOrNull() ?: return false

        try {
            val sendNotification = lspServer.javaClass.getMethod(
                "sendNotification",
                kotlin.jvm.functions.Function1::class.java
            )
            val callback: (org.eclipse.lsp4j.services.LanguageServer) -> Unit = { languageServer ->
                (languageServer as? LuauLanguageServer)?.let(action)
            }
            sendNotification.invoke(lspServer, callback)
        } catch (_: NoSuchMethodException) {
            // Fallback for older IntelliJ versions with lsp4jServer
            try {
                val getLsp4jServer = lspServer.javaClass.getMethod("getLsp4jServer")
                val server = getLsp4jServer.invoke(lspServer) as? LuauLanguageServer ?: return false
                action(server)
            } catch (e: Throwable) {
                LOG.warn("Failed to access LSP server API", e)
                return false
            }
        }
        return true
    }

    private fun HttpExchange.sendResponse(code: Int, body: String) {
        val bytes = body.toByteArray()
        sendResponseHeaders(code, bytes.size.toLong())
        responseBody.write(bytes)
        responseBody.close()
    }

    private inline fun HttpExchange.use(block: () -> Unit) {
        try {
            block()
        } catch (e: Throwable) {
            LOG.warn("Unhandled error in the companion server", e)
            try {
                sendResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Internal Server Error")
            } catch (_: Throwable) {
                // Response may have already been sent
            }
        }
    }
}
