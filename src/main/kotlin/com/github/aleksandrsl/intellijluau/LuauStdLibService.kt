package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.declarations.LuauDeclaration
import com.github.aleksandrsl.intellijluau.declarations.LuauDeclarationsParser
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.PluginPathManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.util.messages.MessageBusConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.concurrent.atomic.AtomicReference

private val LOG = logger<LuauStdLibService>()

@Service(Service.Level.PROJECT)
class LuauStdLibService(val project: Project, private val coroutineScope: CoroutineScope) : Disposable {
    private var stdlibDeclarations: Map<String, LuauDeclaration> = emptyMap()
    private var messageBusConnection: MessageBusConnection? = null
    private val loadingDeferred = AtomicReference<Deferred<Unit>?>(null)


    init {
        messageBusConnection = project.messageBus.connect(this)
        messageBusConnection?.subscribe(
            ProjectSettingsState.TOPIC,
            object : ProjectSettingsState.SettingsChangeListener {
                override fun settingsChanged(e: ProjectSettingsState.SettingsChangedEvent) {
                    if (e.newState.robloxSecurityLevel != e.oldState.robloxSecurityLevel) {
                        loadStdLibDeclarations()
                    }
                }
            })
        loadStdLibDeclarations()
    }

    private fun loadStdLibDeclarations() {
        loadingDeferred.get()?.cancel()
        val settings = ProjectSettingsState.getInstance(project)

        // Calling this from coroutine scope produces garbage temporary file location
        // C:\Users\slale\.gradle\caches\8.9\transforms\2c8a365b11e772d43ecee417107d45cc\transformed\WebStorm-2024.3.4-win\typeDeclarations\globalTypes.None.d.luau
        val file = PluginPathManager.getPluginResource(
            javaClass, "typeDeclarations/globalTypes.${settings.robloxSecurityLevel.name}.d.luau"
        )

        loadingDeferred.set(coroutineScope.async(Dispatchers.IO) {
            if (file?.exists() == true) {
                try {
                    val parser = LuauDeclarationsParser()
                    stdlibDeclarations = parser.parse(file.path)
                } catch (e: Exception) {
                    LOG.error("Failed to parse stdlib declarations", e)
                }
            }
            // Adhoc solution. I'm surprised it works and doesn't complain that it's run from IO.
            DaemonCodeAnalyzer.getInstance(project).restart()
        })
    }

    // TODO (AleksandrSl 08/04/2025): I ned a different process for Enum,
    //  they are not being resolved by the first name, because it's Enum
    fun resolveReference(name: String): LuauDeclaration? = stdlibDeclarations[name]

    companion object {
        fun getInstance(project: Project): LuauStdLibService =
            project.getService(LuauStdLibService::class.java)
    }

    override fun dispose() {
    }
}
