package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.*
import com.github.aleksandrsl.intellijluau.cli.SourcemapGeneratorCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.EDT
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.AsyncFileListener
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.events.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

private val LOG = logger<IdeaWatcherSourcemapGenerator>()

class IdeaWatcherSourcemapGenerator(private val project: Project, private val coroutineScope: CoroutineScope) :
    SourcemapGenerator {
    private var watcherJob: Job? = null
    private var isActive = false
    private var fileListenerDisposable: Disposable? = null
    private lateinit var processQueue: Channel<Unit>
    private val projectDir: VirtualFile?

    override val name: String = "custom"

    init {
        LOG.info("Initializing file watcher for project ${project.name}")
        projectDir = project.guessProjectDir()
    }

    override fun start() {
        if (projectDir == null) {
            SourcemapGenerator.notifications().showProjectNotification(
                "Cannot guess the project root. You're unlucky", NotificationType.ERROR, project
            )
            return
        }
        if (isActive) return
        processQueue = Channel(Channel.CONFLATED)
        fileListenerDisposable =
            Disposer.newDisposable(LuauPluginDisposable.getInstance(project), "FileListenerDisposable")
        // Regenerate sourcemap on the first start
        queueSourcemapRegeneration()

        LOG.info("Starting file watcher for project ${project.name}")

        VirtualFileManager.getInstance().addAsyncFileListener(
            object : AsyncFileListener {
                override fun prepareChange(events: List<VFileEvent>): AsyncFileListener.ChangeApplier? {
                    val luaFileEvents =
                        events.filter { it is VFileMoveEvent || it is VFileCopyEvent || it is VFileCreateEvent || it is VFileDeleteEvent || (it is VFilePropertyChangeEvent && it.propertyName == VirtualFile.PROP_NAME) }
                            .filter {
                                // Should be fine to feed the whole path into getFileTypeByFileName,
                                // it searches for the last extension inside
                                FileTypeRegistry.getInstance()
                                    .getFileTypeByFileName(it.path) == LuauFileType && it.path.startsWith(projectDir.path)
                            }

                    if (luaFileEvents.isEmpty()) return null

                    return object : AsyncFileListener.ChangeApplier {
                        override fun afterVfsChange() {
                            LOG.debug("Processing file events: ${luaFileEvents.joinToString(",")}")
                            queueSourcemapRegeneration()
                        }
                    }
                }
            }, fileListenerDisposable!!
        )

        watcherJob = coroutineScope.launch(Dispatchers.IO) {
            for (item in processQueue) {
                if (!isActive) break
                try {
                    regenerateSourcemap()
                } catch (e: Exception) {
                    LuauNotifications.pluginNotifications().createNotification(
                        LuauBundle.message("luau.sourcemap.generation.failed"), "Error: ${e}.", NotificationType.ERROR
                    ).notify(project)
                }
            }
        }

        isActive = true
    }

    // Should I make this synchronized? I think not, because service shouldn't be called in parallel.
    override suspend fun stop() {
        if (!isActive) return
        isActive = false
        LOG.info("Stopping file watcher for project ${project.name}")

        processQueue.close()
        watcherJob?.cancel()
        fileListenerDisposable?.let {
            Disposer.dispose(it)
            fileListenerDisposable = null
        }
        watcherJob = null
    }

    override fun isRunning(): Boolean {
        return isActive
    }

    private fun queueSourcemapRegeneration() {
        processQueue.trySend(Unit)
    }

    private suspend fun regenerateSourcemap() {
        LOG.info("Regenerating sourcemap for project ${project.name}")
        withContext(Dispatchers.IO) {
            try {
                val output = SourcemapGeneratorCli.generate(project)

                withContext(Dispatchers.EDT) {
                    if (output.exitCode != 0) {
                        notifyError("Exit code: ${output.exitCode}. ${if (output.stdout.isNotBlank()) "Output: ${output.stdout}" else ""}. ${if (output.stderr.isNotBlank()) "Error: ${output.stderr}" else ""}")
                    }
                }
            } catch (e: IOException) {
                notifyError(e.message ?: "")
            } catch (e: InterruptedException) {
                notifyError(e.message ?: "")
            }
        }
    }

    private fun notifyError(message: String) {
        LuauNotifications.pluginNotifications().createNotification(
            LuauBundle.message("luau.sourcemap.generation.failed"), message, NotificationType.ERROR
        ).addAction(NotificationAction.createSimple("Open settings") {
            ShowSettingsUtil.getInstance().showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
        }).notify(project)
    }
}
