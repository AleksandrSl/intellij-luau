package com.github.aleksandrsl.intellijluau.tools

import com.github.aleksandrsl.intellijluau.lsp.LspConfiguration
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import kotlin.io.path.exists

private val LOG = logger<LspCli>()

/**
 * Interact with external `Lsp` process.
 */
class LspCli(private val project: Project, private val lspConfiguration: LspConfiguration.Enabled) {

    fun createLspCli(): GeneralCommandLine {
        return GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withWorkDirectory(project.basePath)
            withCharset(Charsets.UTF_8)
            withExePath(lspConfiguration.executablePath.toString())
            addParameter("lsp")
            // Maybe it makes sense to check for existence here as well, but since these files are partially
            // user-configured, it's good that they see the errors if the files are missing
            lspConfiguration.definitions.forEach {
                addParameter("--definitions")
                addParameter(it.toString())
            }
            lspConfiguration.docs.filter { it.exists() }.forEach {
                addParameter("--docs")
                addParameter(it.toString())
            }
        }
    }


    fun queryVersion(): Version {
        val firstLine = CapturingProcessHandler(GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withCharset(Charsets.UTF_8)
            withWorkDirectory(project.basePath)
            withExePath(lspConfiguration.executablePath.toString())
            addParameter("--version")
//                 TODO: Do lazy reading?
        }).runProcess().stdoutLines.first()
        return Version.parse(firstLine)
    }

    /**
     * Lists the Luau FFlags known to the LSP together with their effective default values (`luau-lsp --show-flags`).
     * Throws if the process can't be run or exits with an error.
     */
    fun queryFlags(): List<LspFlag> {
        val output = CapturingProcessHandler(GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withCharset(Charsets.UTF_8)
            withWorkDirectory(project.basePath)
            withExePath(lspConfiguration.executablePath.toString())
            addParameter("--show-flags")
        }).runProcess(10_000)
        if (output.isTimeout || output.exitCode != 0) {
            throw IllegalStateException("Failed to list LSP flags: ${output.stderr.ifBlank { "exit code ${output.exitCode}" }}")
        }
        return parseShowFlagsOutput(output.stdoutLines)
    }
}

enum class LspFlagType { Bool, Int }

data class LspFlag(val name: String, val type: LspFlagType, val defaultValue: String)

/**
 * Parses `luau-lsp --show-flags` output:
 * ```
 * Available flags:
 *   LuauSolverV2=false
 *   LuauRecursionLimit=1000
 * ```
 * The type is inferred from the value, lines that are neither boolean nor integer are skipped.
 */
fun parseShowFlagsOutput(lines: List<String>): List<LspFlag> = lines.mapNotNull { line ->
    val trimmed = line.trim()
    val eq = trimmed.indexOf('=')
    if (eq <= 0) return@mapNotNull null
    val name = trimmed.substring(0, eq)
    val value = trimmed.substring(eq + 1)
    when {
        value == "true" || value == "false" -> LspFlag(name, LspFlagType.Bool, value)
        value.toIntOrNull() != null -> LspFlag(name, LspFlagType.Int, value)
        else -> null
    }
}
