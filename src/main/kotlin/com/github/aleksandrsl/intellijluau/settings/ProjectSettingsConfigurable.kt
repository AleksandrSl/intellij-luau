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

    val component: ProjectSettingsComponent?
        get() = _component

    override fun createComponent(): JComponent {
        return ProjectSettingsComponent(project.service<LuauCliService>(), project.guessProjectDir(), project).apply {
            lspPath = settings.lspPath
            styLuaPath = settings.styLuaPath
            runStyLuaOnSave = settings.runStyLuaOnSave
            robloxSecurityLevel = settings.robloxSecurityLevel.name
            customDefinitionsPaths = settings.customDefinitionsPaths
            robloxCliPath = settings.robloxCliPath
            generateSourceMaps = settings.generateSourceMapsFromRbxp
            rbxpPath = settings.rbxpForSourcemapPath
        }.also {
            _component = it
        }.panel
    }

    override fun isModified(): Boolean {
        return settings.lspPath != _component?.lspPath || settings.styLuaPath != _component?.styLuaPath || settings.runStyLuaOnSave != _component?.runStyLuaOnSave || settings.robloxSecurityLevel.name != _component?.robloxSecurityLevel || settings.customDefinitionsPaths != _component?.customDefinitionsPaths || settings.generateSourceMapsFromRbxp != _component?.generateSourceMaps || settings.rbxpForSourcemapPath != _component?.rbxpPath || settings.robloxCliPath != _component?.robloxCliPath
    }

    override fun getPreferredFocusedComponent(): JComponent? = _component?.preferredFocusedComponent

    override fun apply() {
        restartLsp()

        settings.update {
            lspPath = _component?.lspPath ?: ""
            robloxSecurityLevel =
                _component?.robloxSecurityLevel?.let { RobloxSecurityLevel.valueOf(it) } ?: defaultRobloxSecurityLevel
            customDefinitionsPaths = _component?.customDefinitionsPaths ?: listOf()
            styLuaPath = _component?.styLuaPath ?: ""
            runStyLuaOnSave = _component?.runStyLuaOnSave ?: false
            robloxCliPath = _component?.robloxCliPath ?: ""
            generateSourceMapsFromRbxp = _component?.generateSourceMaps ?: true
            rbxpForSourcemapPath = _component?.rbxpPath ?: ""
        }
    }

    override fun reset() {
        _component?.run {
            lspPath = settings.lspPath
            styLuaPath = settings.styLuaPath
            runStyLuaOnSave = settings.runStyLuaOnSave
            robloxSecurityLevel = settings.robloxSecurityLevel.name
            customDefinitionsPaths = settings.customDefinitionsPaths
            robloxCliPath = settings.robloxCliPath
            generateSourceMaps = settings.generateSourceMapsFromRbxp
            rbxpPath = settings.rbxpForSourcemapPath
        }
    }

    override fun getDisplayName(): String {
        return LuauBundle.message("luau.settings.name")
    }

    override fun disposeUIResources() {
        _component = null
    }

    private fun restartLsp() {
        if (settings.lspPath != _component?.lspPath || settings.robloxSecurityLevel != _component?.robloxSecurityLevel?.let {
                RobloxSecurityLevel.valueOf(
                    it
                )
            } || settings.customDefinitionsPaths != _component?.customDefinitionsPaths) {
            restartLspServerAsync(project)
        }
    }

    companion object {
        const val CONFIGURABLE_ID = "settings.luau"
    }
}
