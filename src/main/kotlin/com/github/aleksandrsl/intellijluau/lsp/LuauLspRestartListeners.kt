@file:Suppress("UnstableApiUsage")

import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager
import com.github.aleksandrsl.intellijluau.lsp.LuauLspServerSupportProvider
import com.github.aleksandrsl.intellijluau.settings.LspConfigurationType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.platform.lsp.api.LspServerManager

private val LOG = logger<LspServerManager>()

class LuauLspManagerLspRestartListener(val project: Project) : LuauLspManager.LspManagerChangeListener {
    override fun settingsChanged(event: LuauLspManager.LspManagerChangedEvent) {
        when (event) {
            is LuauLspManager.LspManagerChangedEvent.ApiDefinitionsUpdated -> {
                project.restartIfNeeded("Roblox api definitions were updated")
            }

            is LuauLspManager.LspManagerChangedEvent.NewLspVersionDownloaded -> {
                val settings = ProjectSettingsState.getInstance(project)
                if (!settings.isLspEnabledAndMinimallyConfigured || settings.lspConfigurationType != LspConfigurationType.Auto) {
                    return
                }
                if (settings.lspVersion == Version.Latest) {
                    val latest = LuauLspManager.getLatestInstalledLspVersion()
                    if (latest == null || event.version >= latest) {
                        project.restartIfNeeded("LSP is updated")
                    }
                    return
                }
                if (settings.lspVersion == event.version) {
                    project.restartIfNeeded("LSP is downloaded")
                }
            }
        }
    }
}

class LuauSettingsLspRestartListener(val project: Project) : ProjectSettingsConfigurable.SettingsChangeListener {
    // TODO (AleksandrSl 19/06/2025): Unless I implement runtime update of settings in LSP I need to restart it on couple more property changes
    override fun settingsChanged(event: ProjectSettingsConfigurable.SettingsChangedEvent) {
        if (event.isChanged(ProjectSettingsState.State::lspPath)
            || event.isChanged(ProjectSettingsState.State::lspVersion)
            || event.isChanged(ProjectSettingsState.State::robloxSecurityLevel)
            || event.isChanged(ProjectSettingsState.State::customDefinitionsPaths)
            || event.isChanged(ProjectSettingsState.State::lspConfigurationType)
            || event.isChanged(ProjectSettingsState.State::platformType)
        ) {
            project.restartIfNeeded("Project settings changed")
        }
    }
}

private fun Project.restartLspServerAsync() {
    ApplicationManager.getApplication().invokeLater({
        LspServerManager.getInstance(this).stopAndRestartIfNeeded(LuauLspServerSupportProvider::class.java)
    }, this.disposed)
}

private fun Project.restartIfNeeded(reason: String) {
    val settings = ProjectSettingsState.getInstance(this)

    if (settings.isLspEnabledAndMinimallyConfigured) {
        LOG.info("Restarting LSP for project ${this.name}: $reason")
        restartLspServerAsync()
    } else {
        LOG.debug("LSP restart skipped for project ${this.name}: LSP not properly configured")
    }
}
