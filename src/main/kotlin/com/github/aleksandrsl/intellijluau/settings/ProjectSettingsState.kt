package com.github.aleksandrsl.intellijluau.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@Service(Service.Level.PROJECT)
@State(name = "LuauPluginSettings", storages = [Storage("luauPlugin.xml")])
class ProjectSettingsState :
    PersistentStateComponent<ProjectSettingsState.State> {
    private var internalState: State = State()

    val lspPath: String
        get() = internalState.lspPath

    val styLuaPath: String
        get() = internalState.styLuaPath

    val sourcemapGenerationCommand: String
        get() = internalState.sourcemapGenerationCommand

    val runStyluaOnSave
        get() = runStyLua == RunStyluaOption.RunOnSave || runStyLua == RunStyluaOption.RunOnSaveAndDisableBuiltinFormatter

    val disableBuiltinFormatter
        get() = runStyLua == RunStyluaOption.RunOnSaveAndDisableBuiltinFormatter

    val runStyLuaInsteadOfFormatter
        get() = runStyLua == RunStyluaOption.RunInsteadOfFormatter

    val runStyLua
        get() = if (internalState.styLuaPath.isNotBlank()) {
            internalState.runStyLua
        } else {
            RunStyluaOption.Disabled
        }

    val robloxSecurityLevel
        get() = internalState.robloxSecurityLevel

    val customDefinitionsPaths
        get() = internalState.customDefinitionsPaths

    val shouldGenerateSourcemap
        get() = internalState.shouldGenerateSourcemap

    val isLspConfiguredAndEnabled
        get() = internalState.isLspConfiguredAndEnabled


    override fun getState(): State {
        return internalState
    }

    override fun loadState(state: State) {
        XmlSerializerUtil.copyBean(state, internalState)
    }

    data class State(
        var lspPath: String = "",
        var styLuaPath: String = "",
        var sourcemapGenerationCommand: String = "",
        var runStyLua: RunStyluaOption = RunStyluaOption.Disabled,
        var robloxSecurityLevel: RobloxSecurityLevel = defaultRobloxSecurityLevel,
        var customDefinitionsPaths: List<String> = listOf(),
        var isLspEnabled: Boolean = true
    ) {
        val shouldGenerateSourcemap: Boolean
            get() = sourcemapGenerationCommand.isNotBlank()
        val isLspConfiguredAndEnabled: Boolean
            get() = lspPath.isNotBlank() && isLspEnabled
    }

    companion object {
        fun getInstance(project: Project): ProjectSettingsState = project.getService(ProjectSettingsState::class.java)
    }
}


enum class RobloxSecurityLevel {
    None,
    LocalUserSecurity,
    PluginSecurity,
    RobloxScriptSecurity,
}

val defaultRobloxSecurityLevel = RobloxSecurityLevel.None

enum class RunStyluaOption {
    Disabled,
    RunOnSave,
    RunOnSaveAndDisableBuiltinFormatter,
    RunInsteadOfFormatter,
}
