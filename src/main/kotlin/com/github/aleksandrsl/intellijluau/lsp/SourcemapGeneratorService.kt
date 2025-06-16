package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.settings.LspSourcemapGenerationType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.showNotification
import com.github.aleksandrsl.intellijluau.util.hasLuauFiles
import com.intellij.ide.util.PropertiesComponent
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.util.messages.MessageBusConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private val LOG = logger<SourcemapGeneratorService>()

private const val ROJO_SUGGESTION_DISMISSED_PROPERTY_NAME = "luau.lsp.sourcemap.generation.rojo.suggest"

@Service(Service.Level.PROJECT)
class SourcemapGeneratorService(private val project: Project, private val coroutineScope: CoroutineScope) : Disposable {
    private var messageBusConnection: MessageBusConnection? = null
    private var generator: SourcemapGenerator? = null

    init {
        messageBusConnection = project.messageBus.connect(this)
        messageBusConnection?.subscribe(
            ProjectSettingsConfigurable.TOPIC, object : ProjectSettingsConfigurable.SettingsChangeListener {
                override fun settingsChanged(event: ProjectSettingsConfigurable.SettingsChangedEvent) {
                    updateStrategyBasedOnSettings(event)
                }
            })

        updateStrategyBasedOnSettings()
    }

    val canRestart: Boolean
        get() = generator != null

    fun restart() {
        if (!canRestart) return
        updateStrategyBasedOnSettings()
    }

    private fun updateStrategyBasedOnSettings(change: ProjectSettingsConfigurable.SettingsChangedEvent? = null) {
        val settings = ProjectSettingsState.getInstance(project)

        val oldGenerator = generator
        // Let's be simple and stop the existing and start the new one.
        if (oldGenerator != null) {
            oldGenerator.stop()
            oldGenerator.dispose()
        }

        coroutineScope.launch {
            val newGenerator = determineStrategy(settings)
            if (newGenerator == null && !PropertiesComponent.getInstance(project)
                    .getBoolean(ROJO_SUGGESTION_DISMISSED_PROPERTY_NAME) && RojoSourcemapGenerator.canUseRojo(project)
            ) {
                suggestRojo()
            }
            generator = newGenerator?.apply { start() }
        }
    }

    private fun suggestRojo() {
        SourcemapGenerator.notifications().showNotification(
            "Enable rojo sourcemap generation?",
            "Found rojo project file and rojo is installed",
            NotificationType.INFORMATION
        ) {
            addAction(NotificationAction.createSimpleExpiring("Enable") {
                ProjectSettingsState.getInstance(project).enableRojoSourcemapGeneration()
            }).addAction(NotificationAction.createSimpleExpiring("Don't show again for this project") {
                PropertiesComponent.getInstance(project).setValue(ROJO_SUGGESTION_DISMISSED_PROPERTY_NAME, true)
            })
        }
    }

    private suspend fun determineStrategy(settings: ProjectSettingsState): SourcemapGenerator? {
        if (!settings.lspSourcemapSupportEnabled || !settings.isLspEnabledAndMinimallyConfigured || !project.hasLuauFiles()) {
            return null
        }

        return when (settings.lspSourcemapGenerationType) {
            LspSourcemapGenerationType.Rojo -> {
                RojoSourcemapGenerator(project, coroutineScope.createChildScope())
            }

            LspSourcemapGenerationType.Manual -> {
                if (settings.lspSourcemapGenerationCommand.isNotBlank()) {
                    if (settings.lspSourcemapGenerationUseIdeaWatcher) {
                        IdeaWatcherSourcemapGenerator(project, coroutineScope.createChildScope())
                    } else {
                        ExternalWatcherSourcemapGenerator(project, coroutineScope.createChildScope())
                    }
                } else {
                    // TODO (AleksandrSl 27/05/2025):
                    null // Manual selected but no command
                }
            }

            LspSourcemapGenerationType.Disabled -> null
        }
    }

    // TODO (AleksandrSl 27/05/2025): Do I really need this?
    private fun CoroutineScope.createChildScope() = CoroutineScope(this.coroutineContext + SupervisorJob())

    override fun dispose() {
        generator?.stop()
        generator?.dispose()
        generator = null
    }

    companion object {

        @JvmStatic
        fun getInstance(project: Project): SourcemapGeneratorService = project.service()
    }
}
