package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.showProjectNotification
import com.github.aleksandrsl.intellijluau.settings.PlatformType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.util.messages.MessageBusConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
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
class CompanionPluginService(private val project: Project, private val coroutineScope: CoroutineScope) : Disposable {
    private var messageBusConnection: MessageBusConnection? = null
    private var server: CompanionServer? = null

    private val operationQueue = Channel<Operation>(Channel.UNLIMITED)

    init {
        messageBusConnection = project.messageBus.connect(this)
        messageBusConnection?.subscribe(
            ProjectSettingsConfigurable.TOPIC, object : ProjectSettingsConfigurable.SettingsChangeListener {
                override fun settingsChanged(event: ProjectSettingsConfigurable.SettingsChangedEvent) {
                    operationQueue.trySend(Operation.Update(event))
                }
            })

        coroutineScope.launch {
            for (op in operationQueue) {
                try {
                    when (op) {
                        is Operation.Update -> updateServer(op.change)
                        is Operation.Stop -> stop()
                    }
                } catch (e: Exception) {
                    LOG.error("Error processing companion plugin operation: $op", e)
                }
            }
        }

        operationQueue.trySend(Operation.Update())
    }

    private fun stop() {
        server?.stop()
        server = null
    }

    private fun shouldServerBeRunning(settings: CompanionRelevantSettings): Boolean {
        return settings.enabled
                && settings.platformType == PlatformType.Roblox
                && settings.isLspEnabledAndMinimallyConfigured
    }

    private fun updateServer(change: ProjectSettingsConfigurable.SettingsChangedEvent? = null) {
        val newSettingsState = change?.newState ?: ProjectSettingsState.getInstance(project).state
        val companionSettings = newSettingsState.toCompanionRelevantSettings()

        if (change != null) {
            val oldCompanionSettings = change.oldState.toCompanionRelevantSettings()
            if (oldCompanionSettings == companionSettings) {
                return
            }
        }

        val oldServer = server
        oldServer?.stop()
        server = null

        if (!shouldServerBeRunning(companionSettings)) {
            if (oldServer != null) {
                notifications()
                    .showProjectNotification(
                        LuauBundle.message("luau.companion.plugin.stopped"),
                        NotificationType.INFORMATION,
                        project
                    )
            }
            return
        }

        try {
            val newServer = CompanionServer(project, companionSettings.port)
            newServer.start()
            server = newServer
            notifications()
                .showProjectNotification(
                    LuauBundle.message("luau.companion.plugin.started", companionSettings.port),
                    NotificationType.INFORMATION,
                    project
                )
        } catch (e: BindException) {
            LOG.warn("Companion plugin port ${companionSettings.port} is already in use", e)
            notifications()
                .showProjectNotification(
                    LuauBundle.message("luau.companion.plugin.port.in.use", companionSettings.port),
                    NotificationType.ERROR,
                    project
                )
        } catch (e: Exception) {
            LOG.error("Failed to start companion plugin server", e)
            notifications()
                .showProjectNotification(
                    LuauBundle.message("luau.companion.plugin.error", e.message ?: "Unknown error"),
                    NotificationType.ERROR,
                    project
                )
        }
    }

    override fun dispose() {
        messageBusConnection?.disconnect()
        messageBusConnection = null
        server?.stop()
        server = null
        operationQueue.close()
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
