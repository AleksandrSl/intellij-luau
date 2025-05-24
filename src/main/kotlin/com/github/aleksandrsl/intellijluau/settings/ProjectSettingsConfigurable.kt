package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.cli.LuauCliService
import com.github.aleksandrsl.intellijluau.lsp.restartLspServerAsync
import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.util.messages.Topic
import javax.swing.JComponent
import kotlin.reflect.KProperty1

class ProjectSettingsConfigurable(val project: Project) : Configurable {

    private var _component: ProjectSettingsComponent? = null
    private val settings = ProjectSettingsState.getInstance(project)

    override fun createComponent(): JComponent {
        return ProjectSettingsComponent(
            project.service<LuauCliService>(),
            settings,
            project,
            ::applyAndsSaveAsDefault,
        ).also {
            _component = it
        }.panel
    }

    override fun isModified(): Boolean {
        return _component?.panel?.isModified() ?: false
    }

    override fun getPreferredFocusedComponent(): JComponent? = _component?.preferredFocusedComponent

    override fun apply() {
        // TODO (AleksandrSl 24/05/2025): Given I'm now using copy on write object, I can just set the value without copy?
//        val oldState = settings.state
        _component?.panel?.apply()
//        val event = SettingsChangedEvent(
//            oldState,
//            settings.state
//        )
//        notifySettingsChanged(event)
//        restartLsp(event)
    }

    override fun reset() {
        _component?.panel?.reset()
    }

    override fun getDisplayName(): String {
        return LuauBundle.message("luau.settings.name")
    }

    override fun disposeUIResources() {
        _component = null
    }

    private fun restartLsp(settingsChangedEvent: SettingsChangedEvent) {
        if (settingsChangedEvent.isChanged(MyState::lspPath)
            || settingsChangedEvent.isChanged(MyState::lspVersion)
            || settingsChangedEvent.isChanged(MyState::robloxSecurityLevel)
            || settingsChangedEvent.isChanged(MyState::customDefinitionsPaths)
            || settingsChangedEvent.isChanged(MyState::lspConfigurationType)
        ) {
            restartLspServerAsync(project)
        }
    }

    interface SettingsChangeListener {
        fun settingsChanged(e: SettingsChangedEvent)
    }

    private fun notifySettingsChanged(event: SettingsChangedEvent) {
        project.messageBus.syncPublisher(TOPIC).settingsChanged(event)
    }

    private fun applyAndsSaveAsDefault() {
        apply()
        LuauDefaultSettingsState.getInstance().save(settings.state)
    }

    class SettingsChangedEvent(val oldState: MyState, val newState: MyState) {
        /** Use it like `event.isChanged(State::foo)` to check whether `foo` property is changed or not */
        fun isChanged(prop: KProperty1<MyState, *>): Boolean = prop.get(oldState) != prop.get(newState)
    }

    companion object {
        const val CONFIGURABLE_ID = "settings.luau"
        val TOPIC = Topic.create(
            "Luau settings changes",
            SettingsChangeListener::class.java,
            Topic.BroadcastDirection.TO_PARENT
        )
    }
}
