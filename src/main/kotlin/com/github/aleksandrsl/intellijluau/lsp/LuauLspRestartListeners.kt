@file:Suppress("UnstableApiUsage")

package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.settings.LspConfigurationType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.showProjectNotification
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.platform.lsp.api.LspServerManager
import com.intellij.platform.lsp.api.LspServerState

private val LOG = logger<LspServerManager>()

class LuauLspManagerLspRestartListener(val project: Project) : LuauLspManager.LspManagerChangeListener {
    override fun settingsChanged(event: LuauLspManager.LspManagerChangedEvent) {
        when (event) {
            is LuauLspManager.LspManagerChangedEvent.ApiDefinitionsUpdated -> {
                project.restartLspServerAsyncIfNeeded("Roblox api definitions were updated", onlyIfRunning = true)
            }

            is LuauLspManager.LspManagerChangedEvent.NewLspVersionDownloaded -> {
                val settings = ProjectSettingsState.getInstance(project)
                if (!settings.isLspEnabledAndMinimallyConfigured || settings.lspConfigurationType != LspConfigurationType.Auto) {
                    return
                }
                if (settings.lspVersion == Version.Latest) {
                    val latest = LuauLspManager.getLatestInstalledLspVersion()
                    if (latest == null || event.version >= latest) {
                        project.restartLspServerAsyncIfNeeded("LSP is updated to ${event.version}")
                    }
                    return
                }
                if (settings.lspVersion == event.version) {
                    project.restartLspServerAsyncIfNeeded("LSP binary is downloaded")
                }
            }
        }
    }
}

class LuauSettingsLspRestartListener(val project: Project) : ProjectSettingsConfigurable.SettingsChangeListener {
    override fun settingsChanged(event: ProjectSettingsConfigurable.SettingsChangedEvent) {
        if (event.isChanged(ProjectSettingsState.State::lspPath)
            || event.isChanged(ProjectSettingsState.State::lspVersion)
            || event.isChanged(ProjectSettingsState.State::robloxSecurityLevel)
            || event.isChanged(ProjectSettingsState.State::customDefinitionsPaths)
            || event.isChanged(ProjectSettingsState.State::lspConfigurationType)
            || event.isChanged(ProjectSettingsState.State::platformType)
            || event.isChanged(ProjectSettingsState.State::lspSourcemapSupportEnabled)
            || event.isChanged(ProjectSettingsState.State::lspSourcemapFile)
        ) {
            project.restartLspServerAsyncIfNeeded("Project settings changed")
        }
    }
}

private fun Project.restartLspServerAsyncIfNeeded(reason: String?, onlyIfRunning: Boolean = false) {
    ApplicationManager.getApplication().invokeLater({
        val server =
            LspServerManager.getInstance(this).getServersForProvider(LuauLspServerSupportProvider::class.java)
                .firstOrNull()
        val serverIsRunning =
            server !== null && (server.state == LspServerState.Running || server.state == LspServerState.Initializing)
        if (!onlyIfRunning || serverIsRunning) {
            if (reason != null) {
                // This doesn't mean that the server will actually start, but the intention was to start it.
                val message: String? = if (ProjectSettingsState.getInstance(this).isLspEnabledAndMinimallyConfigured) {
                    if (server !== null) "Luau LSP is restarted" else "Luau LSP is started"
                } else {
                    if (serverIsRunning) "Luau LSP is stopped" else null
                }
                if (message != null) {
                    LuauNotifications.pluginNotifications()
                        .showProjectNotification(message, "Reason: $reason", NotificationType.INFORMATION, this)
                }
            }
            LspServerManager.getInstance(this).stopAndRestartIfNeeded(LuauLspServerSupportProvider::class.java)
        }
    }, this.disposed)
}
