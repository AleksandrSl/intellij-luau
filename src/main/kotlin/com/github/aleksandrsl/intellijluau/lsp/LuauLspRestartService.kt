@file:Suppress("UnstableApiUsage")

import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager
import com.github.aleksandrsl.intellijluau.lsp.LuauLspServerSupportProvider
import com.github.aleksandrsl.intellijluau.settings.LspConfigurationType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.platform.lsp.api.LspServerManager
import com.intellij.util.messages.MessageBusConnection

private val LOG = logger<LuauLspRestartService>()

@Service(Service.Level.PROJECT)
class LuauLspRestartService(private val project: Project) : Disposable {
    private var messageBusConnection: MessageBusConnection? = null

    init {
        subscribeToRestartEvents()
    }

    private fun subscribeToRestartEvents() {
        messageBusConnection = project.messageBus.connect(this).apply {
            subscribe(LuauLspManager.TOPIC, object : LuauLspManager.LspManagerChangeListener {
                override fun settingsChanged(event: LuauLspManager.LspManagerChangedEvent) {
                    when (event) {
                        is LuauLspManager.LspManagerChangedEvent.ApiDefinitionsUpdated -> {
                            restartIfNeeded("Roblox api definitions were updated")
                        }

                        is LuauLspManager.LspManagerChangedEvent.NewLspVersionDownloaded -> {
                            val settings = ProjectSettingsState.getInstance(project)
                            if (!settings.isLspEnabledAndMinimallyConfigured || settings.lspConfigurationType != LspConfigurationType.Auto) {
                                return
                            }
                            if (settings.lspVersion == Version.Latest) {
                                val latest = LuauLspManager.getLatestInstalledLspVersion()
                                if (latest == null || event.version >= latest) {
                                    restartIfNeeded("LSP is updated")
                                }
                                return
                            }
                            if (settings.lspVersion == event.version) {
                                restartIfNeeded("LSP is downloaded")
                            }
                        }
                    }
                }
            })
            subscribe(
                ProjectSettingsConfigurable.TOPIC,
                object : ProjectSettingsConfigurable.SettingsChangeListener {
                    override fun settingsChanged(event: ProjectSettingsConfigurable.SettingsChangedEvent) {
                        handleSettingsChange(event)
                    }
                })
        }
    }

    private fun handleSettingsChange(event: ProjectSettingsConfigurable.SettingsChangedEvent) {
        if (event.isChanged(ProjectSettingsState.State::lspPath)
            || event.isChanged(ProjectSettingsState.State::lspVersion)
            || event.isChanged(ProjectSettingsState.State::robloxSecurityLevel)
            || event.isChanged(ProjectSettingsState.State::customDefinitionsPaths)
            || event.isChanged(ProjectSettingsState.State::lspConfigurationType)
        ) {
            restartIfNeeded("Project settings changed")
        }
    }

    private fun restartIfNeeded(reason: String) {
        val settings = ProjectSettingsState.getInstance(project)

        if (settings.isLspEnabledAndMinimallyConfigured) {
            LOG.info("Restarting LSP for project ${project.name}: $reason")
            restartLspServerAsync()
        } else {
            LOG.debug("LSP restart skipped for project ${project.name}: LSP not properly configured")
        }
    }

    private fun restartLspServerAsync() {
        ApplicationManager.getApplication().invokeLater({
            LspServerManager.getInstance(project).stopAndRestartIfNeeded(LuauLspServerSupportProvider::class.java)
        }, project.disposed)
    }

    override fun dispose() {
        messageBusConnection?.dispose()
    }

    companion object {
        fun getInstance(project: Project): LuauLspRestartService = project.service()
    }
}
