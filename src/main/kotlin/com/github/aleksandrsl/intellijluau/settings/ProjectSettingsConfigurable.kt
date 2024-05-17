package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.cli.LuauCliService
import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class ProjectSettingsConfigurable(val project: Project) : Configurable {

    private var component: ProjectSettingsComponent? = null
    private val settings = ProjectSettingsState.instance

    override fun createComponent(): JComponent {
        return ProjectSettingsComponent(project.service<LuauCliService>()).apply {
            lspPath = settings.lspPath
            styLuaPath = settings.styLuaPath
        }.also {
            component = it
        }.panel
    }

    override fun isModified(): Boolean {
        return settings.lspPath != component?.lspPath || settings.styLuaPath != component?.styLuaPath
    }

    override fun getPreferredFocusedComponent(): JComponent? = component?.preferredFocusedComponent

    override fun apply() {
        settings.lspPath = component?.lspPath ?: ""
        settings.styLuaPath = component?.styLuaPath ?: ""
    }

    override fun getDisplayName(): String {
        return "Luau"
    }

    override fun disposeUIResources() {
        component = null
    }
}
