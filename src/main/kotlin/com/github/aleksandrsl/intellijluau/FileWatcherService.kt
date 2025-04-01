package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.cli.RobloxCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.notification.NotificationType
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.EDT
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.AsyncFileListener
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.events.*
import com.intellij.util.messages.MessageBusConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Path.Companion.toPath
import java.io.IOException

private val LOG = logger<FileWatcherService>()

@Service(Service.Level.PROJECT)
class FileWatcherService(private val project: Project, private val coroutineScope: CoroutineScope) : Disposable {
    private var isActive = false
    private var messageBusConnection: MessageBusConnection? = null
    private var fileListenerDisposable: Disposable? = null
    private lateinit var processQueue: Channel<Unit>

    init {
        LOG.warn("Initializing file watcher for project ${project.name}")
        messageBusConnection = project.messageBus.connect()
        messageBusConnection?.subscribe(
            ProjectSettingsState.TOPIC,
            object : ProjectSettingsState.SettingsChangeListener {
                override fun settingsChanged(e: ProjectSettingsState.SettingsChangedEvent) {
                    LOG.warn("Settings changed: $e")
                    if (e.newState.shouldGenerateSourceMapsFromRbxp && !e.oldState.shouldGenerateSourceMapsFromRbxp) {
                        start()
                    } else {
                        stop()
                    }
                }
            })
    }

    fun start() {
        if (isActive) return
        processQueue = Channel(Channel.CONFLATED)
        fileListenerDisposable =
            Disposer.newDisposable(LuauPluginDisposable.getInstance(project), "FileListenerDisposable")
        // Regenerate sourcemap on the first start
        queueSourcempaRegeneration()

        LOG.warn("Starting file watcher for project ${project.name}")

        VirtualFileManager.getInstance().addAsyncFileListener(
            object : AsyncFileListener {
                override fun prepareChange(events: List<VFileEvent>): AsyncFileListener.ChangeApplier? {
                    val index = ProjectFileIndex.getInstance(project)
                    val luaFileEvents =
                        events
                            // Async file listener doesn't know about CreateFile events, so the only way I see is to check for the previous length.
                            // I don't care much if I run it too much, but it's just not effective and we have a lot of modifications.
                            // Maybe BulkListener should be used for these tasks, but they say this one is better since it's async,
                            // but I believe you perform work in the background anyway.
                            // There is a FIleWatcher extension point, but it's undocummented, makes plugin non-dynamic and I don't see it used much
                            .filter { it is VFileMoveEvent || it is VFileCopyEvent || it is VFileCreateEvent || it is VFileDeleteEvent || it is VFileContentChangeEvent && it.oldLength == 0L }
                            .filter {
                                it.file?.also { file ->
                                    LOG.warn("File ${file.name} event: ${file.fileType} and ${index.isInContent(file)}")
                                }?.let { file -> file.fileType == LuauFileType && index.isInContent(file) } ?: false
                            }

                    if (luaFileEvents.isEmpty()) return null

                    return object : AsyncFileListener.ChangeApplier {
                        override fun afterVfsChange() {
                            LOG.warn("Processing file events: ${luaFileEvents.joinToString(",")}")
                            queueSourcempaRegeneration()
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
                        "Sourcemaps generation failed", "Error: ${e}.",
                        NotificationType.ERROR
                    )
                        .notify(project)
                }
            }
        }

        isActive = true
    }

    fun stop() {
        if (!isActive) return


        fileListenerDisposable?.let {
            Disposer.dispose(it)
            fileListenerDisposable = null
        }
        processQueue.close()

        isActive = false
    }

    private fun queueSourcempaRegeneration() {
        processQueue.trySend(Unit)
    }

    private suspend fun regenerateSourcemap() {
        LOG.warn("Regenerating sourcemap for project ${project.name}")
        val projectSettingsState = ProjectSettingsState.getInstance(project)
        withContext(Dispatchers.IO) {
            try {
                val output =
                    RobloxCli(projectSettingsState.robloxCliPath.toPath().toNioPath()).generateSourcemap(project)
                withContext(Dispatchers.EDT) {
                    if (output.exitCode != 0) {
                        LuauNotifications.pluginNotifications().createNotification(
                            "Sourcemaps generation failed", "Exit code: ${output.exitCode}. Output: ${output.stdout}",
                            NotificationType.ERROR
                        )
                            .notify(project)
                    }
                }
            } catch (e: IOException) {
                LuauNotifications.pluginNotifications().createNotification(
                    "Sourcemaps generation failed", e.message ?: "",
                    NotificationType.ERROR
                )
                    .notify(project)
            } catch (e: InterruptedException) {
                LuauNotifications.pluginNotifications().createNotification(
                    "Sourcemaps generation failed", e.message ?: "",
                    NotificationType.ERROR
                )
                    .notify(project)
            }

        }
    }

    override fun dispose() {
        stop()
        messageBusConnection?.disconnect()
    }
}
