package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.cli.LuauCliService
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState.State
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
            settings.state,
            project,
            ::applyAndSaveAsDefault,
        ).also {
            _component = it
        }.panel
    }

    override fun isModified(): Boolean {
        return _component?.panel?.isModified() ?: false
    }

    override fun getPreferredFocusedComponent(): JComponent? = _component?.preferredFocusedComponent

    override fun apply() {
        val oldState = settings.state.copy()
        _component?.panel?.apply()
        val event = SettingsChangedEvent(
            oldState,
            settings.state
        )
        notifySettingsChanged(event)
        // Controversial one,
        // but if the person didn't dismiss the default settings
        // but went and changed the actual ones, using defaults makes little sense.
        dontSuggestDefaultSettingsAgain(project)
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

    interface SettingsChangeListener {
        fun settingsChanged(event: SettingsChangedEvent)
    }

    private fun notifySettingsChanged(event: SettingsChangedEvent) {
        project.messageBus.syncPublisher(TOPIC).settingsChanged(event)
    }

    private fun applyAndSaveAsDefault() {
        apply()
        LuauDefaultSettingsState.getInstance().save(settings.state)
    }

    class SettingsChangedEvent(val oldState: State, val newState: State) {
        /** Use it like `event.isChanged(State::foo)` to check whether `foo` property is changed or not */
        fun isChanged(prop: KProperty1<State, *>): Boolean = prop.get(oldState) != prop.get(newState)
    }

    companion object {
        const val CONFIGURABLE_ID = "settings.luau"
        @Topic.ProjectLevel
        val TOPIC = Topic.create(
            "Luau settings changes",
            SettingsChangeListener::class.java,
            Topic.BroadcastDirection.TO_PARENT
        )
    }
}
