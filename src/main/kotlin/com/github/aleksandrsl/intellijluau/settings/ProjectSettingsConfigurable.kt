package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.cli.LuauCliService
import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class ProjectSettingsConfigurable(val project: Project) : Configurable {

    private var _component: ProjectSettingsComponent? = null
    private val settings = ProjectSettingsState.instance

    val component: ProjectSettingsComponent?
        get() = _component

    override fun createComponent(): JComponent {
        return ProjectSettingsComponent(project.service<LuauCliService>()).apply {
            lspPath = settings.lspPath
            styLuaPath = settings.styLuaPath
            runStyLuaOnSave = settings.runStyLuaOnSave
        }.also {
            _component = it
        }.panel
    }

    override fun isModified(): Boolean {
        return settings.lspPath != _component?.lspPath
                || settings.styLuaPath != _component?.styLuaPath
                || settings.runStyLuaOnSave != _component?.runStyLuaOnSave
    }

    override fun getPreferredFocusedComponent(): JComponent? = _component?.preferredFocusedComponent

    override fun apply() {
        settings.lspPath = _component?.lspPath ?: ""
        settings.styLuaPath = _component?.styLuaPath ?: ""
        settings.runStyLuaOnSave = _component?.runStyLuaOnSave ?: false
    }

    override fun getDisplayName(): String {
        return "Luau"
    }

    override fun disposeUIResources() {
        _component = null
    }

    companion object {
        const val CONFIGURABLE_ID = "settings.luau"
    }
}
