@file:Suppress("UnstableApiUsage")

import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager
import com.github.aleksandrsl.intellijluau.lsp.LuauLspServerSupportProvider
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

private val LOG = logger<LspServerManager>()

class LuauLspManagerLspRestartListener(val project: Project) : LuauLspManager.LspManagerChangeListener {
    override fun settingsChanged(event: LuauLspManager.LspManagerChangedEvent) {
        when (event) {
            is LuauLspManager.LspManagerChangedEvent.ApiDefinitionsUpdated -> {
                project.restartLspServerAsyncIfNeeded("Roblox api definitions were updated")
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

private fun Project.restartLspServerAsyncIfNeeded(reason: String?) {
    ApplicationManager.getApplication().invokeLater({
        if (reason != null) {
            val hasServer =
                LspServerManager.getInstance(this).getServersForProvider(LuauLspServerSupportProvider::class.java)
                    .isNotEmpty()

            // This doesn't mean that the server will actually start, but the intention was to start it.
            val message: String? = if (ProjectSettingsState.getInstance(this).isLspEnabledAndMinimallyConfigured) {
                if (hasServer) "Luau LSP is restarted" else "Luau LSP is started"
            } else {
                if (hasServer) "Luau LSP is stopped" else null
            }
            if (message != null) {
                LuauNotifications.pluginNotifications()
                    .showProjectNotification(message, "Reason: $reason", NotificationType.INFORMATION, this)
            }
        }
        LspServerManager.getInstance(this).stopAndRestartIfNeeded(LuauLspServerSupportProvider::class.java)
    }, this.disposed)
}
