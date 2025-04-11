package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.cli.LuauCliService
import com.github.aleksandrsl.intellijluau.restartLspServerAsync
import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import javax.swing.JComponent

class ProjectSettingsConfigurable(val project: Project) : Configurable {

    private var _component: ProjectSettingsComponent? = null
    private val settings = ProjectSettingsState.getInstance(project)

    override fun createComponent(): JComponent {
        return ProjectSettingsComponent(project.service<LuauCliService>(), project.guessProjectDir(), project, settings.state).also {
            _component = it
        }.panel
    }

    override fun isModified(): Boolean {
        return _component?.panel?.isModified() ?: false
    }

    override fun getPreferredFocusedComponent(): JComponent? = _component?.preferredFocusedComponent

    override fun apply() {
        _component?.panel?.apply()
        // Is it possible to setup a message bus listener in lspProvider and move this?
        restartLsp()
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

    private fun restartLsp() {
//        if (settings.lspPath != _component?.lspPath
//            || settings.robloxSecurityLevel != _component?.robloxSecurityLevel?.let {
//                RobloxSecurityLevel.valueOf(
//                    it
//                )
//            }
//            || settings.customDefinitionsPaths != _component?.customDefinitionsPaths
//            || settings.isLspEnabled != _component?.isLspEnabled
//        ) {
//            restartLspServerAsync(project)
//        }
    }

    companion object {
        const val CONFIGURABLE_ID = "settings.luau"
    }
}
