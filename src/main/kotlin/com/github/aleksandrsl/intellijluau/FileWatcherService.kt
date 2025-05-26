package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.cli.SourcemapGeneratorCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.EDT
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
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
import com.intellij.util.messages.MessageBusConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

private val LOG = logger<FileWatcherService>()

@Service(Service.Level.PROJECT)
class FileWatcherService(private val project: Project, private val coroutineScope: CoroutineScope) : Disposable {
    private var isActive = false
    private var messageBusConnection: MessageBusConnection? = null
    private var fileListenerDisposable: Disposable? = null
    private lateinit var processQueue: Channel<Unit>
    private val projectDir: VirtualFile?

    init {
        LOG.info("Initializing file watcher for project ${project.name}")
        projectDir = project.guessProjectDir()
        messageBusConnection = project.messageBus.connect(this)
        messageBusConnection?.subscribe(
            ProjectSettingsConfigurable.TOPIC, object : ProjectSettingsConfigurable.SettingsChangeListener {
                override fun settingsChanged(e: ProjectSettingsConfigurable.SettingsChangedEvent) {
                    LOG.info("Settings changed, new: ${e.newState.shouldUseWatcherToGenerateSourcemap}, old: ${e.oldState.shouldUseWatcherToGenerateSourcemap}")
                    if (e.newState.shouldUseWatcherToGenerateSourcemap && !e.oldState.shouldUseWatcherToGenerateSourcemap) {
                        start()
                    } else {
                        stop()
                    }
                }
            })

        if (ProjectSettingsState.getInstance(project).shouldUseWatcherToGenerateSourcemap) {
            start()
        }
    }

    fun start() {
        if (projectDir == null) {
            LuauNotifications.pluginNotifications().createNotification(
                "Cannot guess the project root. You're unlucky", NotificationType.ERROR
            ).notify(project)
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
                            LOG.warn("Processing file events: ${luaFileEvents.joinToString(",")}")
                            queueSourcemapRegeneration()
                        }
                    }
                }
            }, fileListenerDisposable!!
        )

        coroutineScope.launch(Dispatchers.IO) {
            for (commandLine in processQueue) {
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

    fun stop() {
        LOG.info("Stopping file watcher for project ${project.name}")
        if (!isActive) return


        fileListenerDisposable?.let {
            Disposer.dispose(it)
            fileListenerDisposable = null
        }
        processQueue.close()

        isActive = false
    }

    private fun queueSourcemapRegeneration() {
        processQueue.trySend(Unit)
    }

    private suspend fun regenerateSourcemap() {
        LOG.info("Regenerating sourcemap for project ${project.name}")
        val projectSettingsState = ProjectSettingsState.getInstance(project)
        if (!projectSettingsState.shouldUseWatcherToGenerateSourcemap) return
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

    override fun dispose() {
        stop()
        messageBusConnection?.disconnect()
    }

    companion object {

        @JvmStatic
        fun getInstance(project: Project): FileWatcherService = project.service()
    }
}
