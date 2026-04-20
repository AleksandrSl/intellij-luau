package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.settings.PlatformType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.showProjectNotification
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.net.BindException

private val LOG = logger<CompanionPluginService>()

private data class CompanionRelevantSettings(
    val enabled: Boolean,
    val port: Int,
    val platformType: PlatformType,
    val isLspEnabledAndMinimallyConfigured: Boolean,
)

private fun ProjectSettingsState.State.toCompanionRelevantSettings() = CompanionRelevantSettings(
    enabled = this.companionPluginEnabled,
    port = this.companionPluginPort,
    platformType = this.platformType,
    isLspEnabledAndMinimallyConfigured = this.isLspEnabledAndMinimallyConfigured,
)

@Service(Service.Level.PROJECT)
class CompanionPluginService(private val project: Project, private val coroutineScope: CoroutineScope) {
    // Owned exclusively by the worker coroutine. Do NOT touch from other threads.
    private var server: CompanionServer? = null

    private val operationQueue = Channel<Operation>(Channel.UNLIMITED)

    init {
        project.messageBus.connect(coroutineScope).subscribe(
            ProjectSettingsConfigurable.TOPIC, object : ProjectSettingsConfigurable.SettingsChangeListener {
                override fun settingsChanged(event: ProjectSettingsConfigurable.SettingsChangedEvent) {
                    operationQueue.trySend(Operation.Update(event))
                }
            })

        coroutineScope.launch {
            try {
                for (op in operationQueue) {
                    try {
                        when (op) {
                            is Operation.Update -> updateServer(op.change)
                            is Operation.Stop -> stop()
                        }
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        LOG.error("Error processing companion plugin operation: $op", e)
                    }
                }
            } finally {
                // Final cleanup: make sure we don't leave a running server behind,
                // regardless of whether we exited normally or via cancellation.
                try {
                    server?.stop()
                } catch (e: Exception) {
                    LOG.warn("Failed to stop companion server during shutdown", e)
                } finally {
                    server = null
                }
            }
        }

        operationQueue.trySend(Operation.Update())
    }

    private suspend fun stop() {
        withContext(Dispatchers.IO) {
            server?.stop()
        }
        server = null
    }

    private fun shouldServerBeRunning(settings: CompanionRelevantSettings): Boolean {
        return settings.enabled && settings.platformType == PlatformType.Roblox && settings.isLspEnabledAndMinimallyConfigured
    }

    private suspend fun updateServer(change: ProjectSettingsConfigurable.SettingsChangedEvent? = null) {
        val newSettingsState = change?.newState ?: ProjectSettingsState.getInstance(project).state
        val companionSettings = newSettingsState.toCompanionRelevantSettings()

        if (change != null) {
            val oldCompanionSettings = change.oldState.toCompanionRelevantSettings()
            if (oldCompanionSettings == companionSettings) {
                return
            }
        }

        val oldServer = server
        server = null
        if (oldServer != null) {
            withContext(Dispatchers.IO) { oldServer.stop() }
        }

        if (!shouldServerBeRunning(companionSettings)) {
            if (oldServer != null) {
                notifications().showProjectNotification(
                    LuauBundle.message("luau.companion.plugin.stopped"), NotificationType.INFORMATION, project
                )
            }
            return
        }

        // Throw CancellationException if we're already shutting down.
        currentCoroutineContext().ensureActive()

        val newServer = try {
            withContext(Dispatchers.IO) {
                CompanionServer(project, companionSettings.port).also { it.start() }
            }
        } catch (e: BindException) {
            LOG.warn("Companion plugin port ${companionSettings.port} is already in use", e)
            notifications().showProjectNotification(
                LuauBundle.message("luau.companion.plugin.port.in.use", companionSettings.port),
                NotificationType.ERROR,
                project
            )
            return
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            LOG.error("Failed to start companion plugin server", e)
            notifications().showProjectNotification(
                LuauBundle.message("luau.companion.plugin.error", e.message ?: "Unknown error"),
                NotificationType.ERROR,
                project
            )
            return
        }

        // If cancellation happened while we were starting the server,
        // stop it and propagate cancellation instead of publishing it as current.
        try {
            currentCoroutineContext().ensureActive()
        } catch (ce: CancellationException) {
            runCatching { newServer.stop() }
                .onFailure { LOG.warn("Failed to stop a freshly started server during cancellation", it) }
            throw ce
        }

        server = newServer
        notifications().showProjectNotification(
            LuauBundle.message("luau.companion.plugin.started", companionSettings.port),
            NotificationType.INFORMATION,
            project
        )
    }

    private sealed class Operation {
        data class Update(
            val change: ProjectSettingsConfigurable.SettingsChangedEvent? = null,
        ) : Operation()

        object Stop : Operation()
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): CompanionPluginService = project.service()

        fun notifications(): NotificationGroup =
            NotificationGroupManager.getInstance().getNotificationGroup("Luau companion plugin")
    }
}
