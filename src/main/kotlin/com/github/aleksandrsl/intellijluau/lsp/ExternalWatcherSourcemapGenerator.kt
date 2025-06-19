package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.cli.SourcemapGeneratorCli
import com.github.aleksandrsl.intellijluau.showNotification
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


open class ExternalWatcherSourcemapGenerator(private val project: Project, private val coroutineScope: CoroutineScope) :
    SourcemapGenerator {

    private var processHandler: OSProcessHandler? = null
    open val name: String = ""

    override fun start() {
        coroutineScope.launch {
            startWatch()
        }
    }

    override fun isRunning(): Boolean {
        return processHandler != null && !processHandler!!.isProcessTerminated
    }

    protected open fun createProcess(): OSProcessHandler {
        return SourcemapGeneratorCli.createProcess(project)
    }

    protected fun startWatch() {
        if (isRunning()) {
            return
        }

        try {
            processHandler = createProcess()
            processHandler?.addProcessListener(GeneratorProcessListener(project, this))
            processHandler?.startNotify()
            SourcemapGenerator.notifications()
                // TODO (AleksandrSl 10/06/2025): Capitalize
                .showNotification("$name sourcemap generator started", NotificationType.INFORMATION, project)
        } catch (e: Exception) {
            SourcemapGenerator.notifications().showNotification(
                LuauBundle.message("luau.sourcemap.generation.failed"),
                "Failed to start $name process: ${e.message}",
                NotificationType.ERROR,
                project,
            )
            processHandler = null // Ensure it's null if start failed
        }
    }

    override fun stop() {
        processHandler?.let {
            if (!it.isProcessTerminated) {
                it.destroyProcess() // Or detachProcess() + terminate gracefully
                SourcemapGenerator.notifications().showNotification(
                    "$name sourcemap generator stopped", NotificationType.INFORMATION, project
                )
            }
        }
        processHandler = null
    }

    internal fun onProcessTerminated(exitCode: Int) {
        val wasRunning = processHandler != null
        processHandler = null

        if (wasRunning) {
            if (exitCode != 0) {
                SourcemapGenerator.notifications().showNotification(
                    LuauBundle.message("luau.sourcemap.generation.failed"),
                    "$name process stopped with exit code $exitCode.",
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
                    "$name sourcemap generator finished", NotificationType.INFORMATION, project
                )
            }
        }
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
                SourcemapGenerator.notifications().showNotification(
                    LuauBundle.message("luau.sourcemap.generation.failed"),
                    "${service.name} process reported errors: ${event.text.take(100)}${if (event.text.length > 100) "..." else ""}",
                    NotificationType.ERROR,
                    project
                )
                errorOutputCollected = true
            }
            // Consider if specific errors should immediately trigger a stop/restart via the service.
        }
    }
}
