package com.github.aleksandrsl.intellijluau.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class ProjectSettingsConfigurable: Configurable {

    private var component: ProjectSettingsComponent? = null
    private val settings = ProjectSettingsState.instance

    override fun createComponent(): JComponent {
        return ProjectSettingsComponent().apply {
            lspPath = settings.lspPath
        }.also {
            component = it
        }.panel
    }

    override fun isModified(): Boolean {
        return settings.lspPath != component?.lspPath
    }

    override fun getPreferredFocusedComponent(): JComponent?  = component?.preferredFocusedComponent

    override fun apply() {
        settings.lspPath = component?.lspPath ?: ""
    }

    override fun getDisplayName(): String {
        return "Luau"
    }

    override fun disposeUIResources() {
        component = null
    }
}
