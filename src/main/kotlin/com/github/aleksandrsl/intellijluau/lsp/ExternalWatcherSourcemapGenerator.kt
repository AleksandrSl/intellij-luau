package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.cli.SourcemapGeneratorCli
import com.github.aleksandrsl.intellijluau.showProjectNotification
import com.github.aleksandrsl.intellijluau.util.capitalize
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


open class ExternalWatcherSourcemapGenerator(private val project: Project, private val coroutineScope: CoroutineScope) :
    SourcemapGenerator {

    private var processHandler: OSProcessHandler? = null
    private var stoppedManually: Boolean = false
    override val name: String = "custom"

    override fun start() {
        coroutineScope.launch {
            doStart()
        }
    }

    // Extracted into a separated method, so it can be called from the overridden start
    protected fun doStart() {
        if (isRunning()) {
            return
        }

        try {
            processHandler = createProcess().apply {
                addProcessListener(GeneratorProcessListener(project, this@ExternalWatcherSourcemapGenerator))
                startNotify()
            }
        } catch (e: Exception) {
            SourcemapGenerator.notifications().showProjectNotification(
                LuauBundle.message("luau.sourcemap.generation.failed"),
                "Failed to start $name process: ${e.message}",
                NotificationType.ERROR,
                project,
            )
            processHandler = null
            stoppedManually = false
        }
    }

    override fun isRunning(): Boolean {
        return processHandler?.let { !it.isProcessTerminated && !it.isProcessTerminating } ?: false
    }

    protected open fun createProcess(): OSProcessHandler {
        return SourcemapGeneratorCli.createProcess(project)
    }

    override suspend fun stop() {
        if (isRunning()) {
            processHandler?.let {
                stoppedManually = true
                // I took a look at plugins in the intellij plugin, and most of them just destroy the process without waiting for it to actually die.
                // there is a killable process, but it doesn't work on Mac anyway.
                withContext(Dispatchers.IO) {
                    it.destroyProcess()
                }
            }
            processHandler = null
        }
    }

    internal fun onProcessTerminated(exitCode: Int) {
        val wasRunning = processHandler != null
        processHandler = null

        if (wasRunning) {
            // Killing a process is not possible on macOS, it seems, so this is more UX gentle to avoid extra notifications
            if (exitCode != 0 && !(stoppedManually && exitCode == -1)) {
                SourcemapGenerator.notifications().showProjectNotification(
                    LuauBundle.message("luau.sourcemap.generation.failed"),
                    "$name process stopped with exit code $exitCode.".capitalize(),
                    NotificationType.ERROR,
                    project
                ) {
                    addAction(NotificationAction.createSimpleExpiring("Restart watcher") {
                        SourcemapGeneratorService.getInstance(project).restart()
                    })
                }
            } else if (!stoppedManually) {
                SourcemapGenerator.notifications().showProjectNotification(
                    "$name sourcemap generator finished".capitalize(), NotificationType.INFORMATION, project
                )
            }
        }
        stoppedManually = false
    }
}

class GeneratorProcessListener(
    private val project: Project, private val service: ExternalWatcherSourcemapGenerator
) : ProcessListener {

    private var errorOutputCollected: Boolean = false

    override fun startNotified(event: ProcessEvent) {
        errorOutputCollected = false // Reset for a new process run
    }

    override fun processTerminated(event: ProcessEvent) {
        service.onProcessTerminated(event.exitCode)
    }

    override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
        if (outputType == ProcessOutputTypes.STDERR && event.text.isNotBlank()) {
            // Basic error detection: any output on STDERR is considered a sign of trouble.
            if (!errorOutputCollected) { // Show notification only once per run for general errors
                SourcemapGenerator.notifications().showProjectNotification(
                    LuauBundle.message("luau.sourcemap.generation.failed"),
                    "${service.name} process reported errors: ${event.text.take(100)}${if (event.text.length > 100) "..." else ""}".capitalize(),
                    NotificationType.ERROR,
                    project
                )
                errorOutputCollected = true
            }
            // Consider if specific errors should immediately trigger a stop/restart via the service.
        }
    }
}
