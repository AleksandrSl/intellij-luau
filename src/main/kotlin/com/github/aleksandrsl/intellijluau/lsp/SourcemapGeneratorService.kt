package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.settings.LspSourcemapGenerationType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.showProjectNotification
import com.github.aleksandrsl.intellijluau.util.capitalize
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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

private val LOG = logger<SourcemapGeneratorService>()

private const val ROJO_SUGGESTION_DISMISSED_PROPERTY_NAME = "luau.lsp.sourcemap.generation.rojo.suggest"

private data class SourcemapRelevantSettings(
    val lspSourcemapSupportEnabled: Boolean,
    val lspSourcemapGenerationType: LspSourcemapGenerationType,
    val lspSourcemapGenerationUseIdeaWatcher: Boolean,
    val lspSourcemapGenerationCommand: String,
    val lspRojoProjectFile: String,
    val isLspEnabledAndMinimallyConfigured: Boolean
)

private fun ProjectSettingsState.State.toSourcemapRelevantSettings() = SourcemapRelevantSettings(
    lspSourcemapSupportEnabled = this.lspSourcemapSupportEnabled,
    lspSourcemapGenerationType = this.lspSourcemapGenerationType,
    lspSourcemapGenerationUseIdeaWatcher = this.lspSourcemapGenerationUseIdeaWatcher,
    lspSourcemapGenerationCommand = this.lspSourcemapGenerationCommand,
    lspRojoProjectFile = this.lspRojoProjectFile,
    isLspEnabledAndMinimallyConfigured = this.isLspEnabledAndMinimallyConfigured
)

@Service(Service.Level.PROJECT)
class SourcemapGeneratorService(private val project: Project, private val coroutineScope: CoroutineScope) : Disposable {
    private var messageBusConnection: MessageBusConnection? = null
    private var generator: SourcemapGenerator? = null

    private val operationQueue = Channel<Operation>(Channel.UNLIMITED)


    init {
        messageBusConnection = project.messageBus.connect(this)
        messageBusConnection?.subscribe(
            ProjectSettingsConfigurable.TOPIC, object : ProjectSettingsConfigurable.SettingsChangeListener {
                override fun settingsChanged(event: ProjectSettingsConfigurable.SettingsChangedEvent) {
                    operationQueue.trySend(Operation.UpdateStrategy(event))
                }
            })

        coroutineScope.launch {
            for (op in operationQueue) {
                try {
                    when (op) {
                        is Operation.UpdateStrategy -> updateGeneratorBasedOnSettings(op.change)
                        is Operation.Stop -> stop()
                    }
                } catch (e: Exception) {
                    LOG.error("Error processing operation: $op", e)
                }
            }
        }

        operationQueue.trySend(Operation.UpdateStrategy())
    }

    private suspend fun stop() {
        generator?.stop()
        generator = null
    }

    // TODO (AleksandrSl 19/06/2025): Allow restart if the generator is configured but missing because it died
    val canRestart: Boolean
        get() = generator != null

    fun restart() {
        if (!canRestart) return
        operationQueue.trySend(Operation.UpdateStrategy())
    }

    private suspend fun updateGeneratorBasedOnSettings(
        change: ProjectSettingsConfigurable.SettingsChangedEvent? = null,
    ) {
        val newSettingsState = change?.newState ?: ProjectSettingsState.getInstance(project).state
        val sourcemapSettings = newSettingsState.toSourcemapRelevantSettings()

        // If there's a change, check if the relevant settings have actually changed.
        if (change != null) {
            val oldSourcemapSettings = change.oldState.toSourcemapRelevantSettings()
            if (oldSourcemapSettings == sourcemapSettings) {
                // No relevant settings changed, no need to restart the generator
                return
            }
        }

        val oldGenerator = generator
        // Let's be simple and stop the existing and start the new one.
        oldGenerator?.stop()

        val newGenerator = determineStrategy(sourcemapSettings)
        if (newGenerator == null && change == null && !PropertiesComponent.getInstance(project)
                .getBoolean(ROJO_SUGGESTION_DISMISSED_PROPERTY_NAME) && doesSourcemapGenerationMakeSense(
                sourcemapSettings
            ) && RojoSourcemapGenerator.canUseRojo(
                project
            )
        ) {
            suggestRojo()
        }
        generator = newGenerator?.apply { start() }

        if (oldGenerator != null && newGenerator != null) {
            val message = if (oldGenerator::class != newGenerator::class) {
                "Switching from ${oldGenerator.name} to ${newGenerator.name} sourcemap generator"
            } else {
                "${oldGenerator.name} sourcemap generator restarted".capitalize()
            }
            SourcemapGenerator.notifications()
                .showProjectNotification(
                    message,
                    NotificationType.INFORMATION,
                    project
                )
        } else if (oldGenerator != null) {
            SourcemapGenerator.notifications()
                .showProjectNotification(
                    "${oldGenerator.name} sourcemap generator stopped".capitalize(),
                    NotificationType.INFORMATION,
                    project
                )
        } else if (generator != null) {
            SourcemapGenerator.notifications()
                .showProjectNotification(
                    "${generator?.name} sourcemap generator started".capitalize(),
                    NotificationType.INFORMATION,
                    project
                )
        }
    }

    private fun suggestRojo() {
        SourcemapGenerator.notifications().showProjectNotification(
            "Enable rojo sourcemap generation?",
            "Found rojo project file and rojo is installed",
            NotificationType.INFORMATION,
            project
        ) {
            addAction(NotificationAction.createSimpleExpiring("Enable") {
                ProjectSettingsState.getInstance(project).enableRojoSourcemapGeneration()
            }).addAction(NotificationAction.createSimpleExpiring("Don't suggest again for this project") {
                PropertiesComponent.getInstance(project).setValue(ROJO_SUGGESTION_DISMISSED_PROPERTY_NAME, true)
            })
        }
    }

    private suspend fun determineStrategy(settings: SourcemapRelevantSettings): SourcemapGenerator? {
        if (!settings.lspSourcemapSupportEnabled || !project.hasLuauFiles()) {
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

    private suspend fun doesSourcemapGenerationMakeSense(settings: SourcemapRelevantSettings): Boolean {
        return settings.isLspEnabledAndMinimallyConfigured && settings.lspSourcemapSupportEnabled && project.hasLuauFiles()
    }

    override fun dispose() {
        messageBusConnection?.disconnect()
        messageBusConnection = null
        // Send a final stop operation and close the queue.
        // The processing coroutine will execute it and then terminate as the channel is closed.
        operationQueue.trySend(Operation.Stop)
        operationQueue.close()
        generator = null
    }

    // A sealed class to represent the allowed operations
    private sealed class Operation {
        data class UpdateStrategy(
            val change: ProjectSettingsConfigurable.SettingsChangedEvent? = null,
        ) : Operation()

        object Stop : Operation()
    }

    companion object {

        @JvmStatic
        fun getInstance(project: Project): SourcemapGeneratorService = project.service()
    }
}
