package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.cli.RojoCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.showNotification
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessListener
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.io.path.Path
import kotlin.io.path.exists

const val DEFAULT_ROJO_PROJECT_FILE = "default.project.json"

class RojoSourcemapGenerator(private val project: Project, private val coroutineScope: CoroutineScope) :
    SourcemapGenerator {

    private var rojoProcessHandler: ProcessHandler? = null
    private val rojoCli = RojoCli()

    override fun start() {
        coroutineScope.launch {
            if (checkIntegrity()) {
                startRojoWatch()
            }
        }
    }

    override fun isRunning(): Boolean {
        return rojoProcessHandler != null && !rojoProcessHandler!!.isProcessTerminated
    }

    private fun startRojoWatch() {
        if (isRunning()) {
            return
        }

        val settings = ProjectSettingsState.getInstance(project)

        try {
            rojoProcessHandler =
                rojoCli.generateSourcemap(project, settings.lspRojoProjectFile, settings.lspSourcemapFile)
            rojoProcessHandler?.addProcessListener(RojoProcessListener(project, this))
            rojoProcessHandler?.startNotify()
            SourcemapGenerator.notifications().showNotification("Rojo sourcemap watcher started", NotificationType.INFORMATION, project)
        } catch (e: Exception) {
            SourcemapGenerator.notifications().showNotification(
                LuauBundle.message("luau.sourcemap.generation.failed"),
                "Failed to start Rojo process: ${e.message}",
                NotificationType.ERROR,
                project,
            )
            rojoProcessHandler = null // Ensure it's null if start failed
        }
    }

    override fun stop() {
        rojoProcessHandler?.let {
            if (!it.isProcessTerminated) {
                it.destroyProcess() // Or detachProcess() + terminate gracefully if Rojo supports it
                SourcemapGenerator.notifications().showNotification(
                    "Rojo Sourcemap Watcher stopped", NotificationType.INFORMATION, project
                )
            }
        }
        rojoProcessHandler = null
    }

    internal fun onRojoProcessTerminated(exitCode: Int) {
        val wasRunning = rojoProcessHandler != null
        rojoProcessHandler = null

        if (wasRunning) {
            if (exitCode != 0) {
                SourcemapGenerator.notifications().showNotification(
                    LuauBundle.message("luau.sourcemap.generation.failed"),
                    "Rojo process stopped with exit code $exitCode.",
                    NotificationType.ERROR,
                    project
                ) {
                    // By this point the process should have been killed already, so we can just restart it.
                    addAction(NotificationAction.createSimpleExpiring("Restart watcher") {
                        start()
                    })
                }
            } else {
                SourcemapGenerator.notifications().showNotification(
                    "Rojo sourcemap watcher finished", NotificationType.INFORMATION, project
                )
            }
        }
    }

    // Check that the files configured exist. It's easier than trying to get that process failed because of this.
    // If it fails anyway there will be a different notification.
    private suspend fun checkIntegrity(): Boolean {
        val settings = ProjectSettingsState.getInstance(project)

        val version = withContext(Dispatchers.IO) {
            try {
                RojoCli.queryVersion()
            } catch (e: Exception) {
                null
            }
        }
        if (version == null) {
            SourcemapGenerator.notifications().showNotification(
                LuauBundle.message("luau.sourcemap.generation.rojo.title"),
                "Sourcemap generation requires Rojo installed globally. Please, install it or configure sourcemap generation.",
                NotificationType.INFORMATION,
            ) {
                addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.open.settings")) {
                    ShowSettingsUtil.getInstance()
                        .showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                })
            }
            return false
        }

        if (!Path(settings.lspRojoProjectFile).exists()) {
            SourcemapGenerator.notifications().showNotification(
                LuauBundle.message("luau.sourcemap.generation.rojo.title"),
                "Rojo project file (${settings.lspRojoProjectFile}) doesn't exist. You can configure which file to use in the settings",
                NotificationType.INFORMATION,
            ) {
                addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.open.settings")) {
                    ShowSettingsUtil.getInstance()
                        .showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                })
            }
            return false
        }
        return true
    }

    override fun dispose() {
        stop()
    }

    companion object {
        suspend fun canUseRojo(project: Project): Boolean {
            val root = project.basePath
            if (root == null) {
                return false
            }
            if (!Path(root).resolve(DEFAULT_ROJO_PROJECT_FILE).exists()) {
                return false
            }

            val version = withContext(Dispatchers.IO) {
                try {
                    RojoCli.queryVersion()
                } catch (e: Exception) {
                    null
                }
            }

            return version != null
        }
    }
}

class RojoProcessListener(
    private val project: Project, private val service: RojoSourcemapGenerator
) : ProcessListener {

    private var errorOutputCollected: Boolean = false

    override fun startNotified(event: ProcessEvent) {
        errorOutputCollected = false // Reset for a new process run
    }

    override fun processTerminated(event: ProcessEvent) {
        service.onRojoProcessTerminated(event.exitCode)
    }

    override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
        if (outputType == ProcessOutputTypes.STDERR && event.text.isNotBlank()) {
            // Basic error detection: any output on STDERR is considered a sign of trouble.
            // You might want more sophisticated parsing if Rojo has specific error patterns.
            if (!errorOutputCollected) { // Show notification only once per run for general errors
                SourcemapGenerator.notifications().showNotification(
                    LuauBundle.message("luau.sourcemap.generation.failed"),
                    "Rojo process reported errors: ${event.text.take(100)}${if (event.text.length > 100) "..." else ""}",
                    NotificationType.ERROR,
                    project
                )
                errorOutputCollected = true
            }
            // Consider if specific errors should immediately trigger a stop/restart via the service.
        }
    }
}
