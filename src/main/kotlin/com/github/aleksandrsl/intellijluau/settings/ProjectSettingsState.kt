package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

// TODO (AleksandrSl 11/05/2025): Try using - SerializablePersistentStateComponent. https://plugins.jetbrains.com/docs/intellij/persisting-state-of-components.html#implementing-the-persistentstatecomponent-interface
@Service(Service.Level.PROJECT)
@State(name = "LuauPluginSettings", storages = [Storage("luauPlugin.xml")])
class ProjectSettingsState :
    PersistentStateComponent<ProjectSettingsState.State> {
    private var internalState: State = State()

    var lspVersion: Version.Semantic?
        get() {
            val version = internalState.lspVersion
            if (version == null) {
                return null
            }
            return try {
                Version.Semantic.parse(version)
            } catch (e: Exception) {
                null
            }
        }
        set(value) {
            internalState.lspVersion = value?.toString()
        }

    val lspConfigurationType: LspConfigurationType
        get() = internalState.lspConfigurationType

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

    val isLspEnabled
        get() = internalState.isLspEnabled

    val lspUseLatest
        get() = internalState.lspUseLatest

    override fun getState(): State {
        return internalState
    }

    override fun loadState(state: State) {
        XmlSerializerUtil.copyBean(state, internalState)
    }

    fun loadDefaultSettings() {
        val defaults = LuauDefaultSettingsState.getInstance().state
        internalState.lspUseLatest = defaults.lspUseLatest
        internalState.lspConfigurationType = defaults.lspConfigurationType
        internalState.styLuaPath = defaults.styLuaPath
        internalState.lspVersion = defaults.lspVersion
        internalState.lspPath = defaults.lspPath
        internalState.sourcemapGenerationCommand = defaults.sourcemapGenerationCommand
        internalState.runStyLua = defaults.runStyLua
        internalState.robloxSecurityLevel = defaults.robloxSecurityLevel
        internalState.customDefinitionsPaths = defaults.customDefinitionsPaths
    }

    data class State(
        override var lspUseLatest: Boolean = true,
        override var lspVersion: String? = null,
        override var lspConfigurationType: LspConfigurationType = LspConfigurationType.Auto,
        override var lspPath: String = "",
        override var styLuaPath: String = "",
        override var sourcemapGenerationCommand: String = "",
        override var runStyLua: RunStyluaOption = RunStyluaOption.Disabled,
        override var robloxSecurityLevel: RobloxSecurityLevel = defaultRobloxSecurityLevel,
        override var customDefinitionsPaths: List<String> = listOf(),
    ) : ShareableProjectSettingsState

    companion object {
        fun getInstance(project: Project): ProjectSettingsState = project.getService(ProjectSettingsState::class.java)
    }
}

interface ShareableProjectSettingsState {
    val lspConfigurationType: LspConfigurationType
    val lspVersion: String?
    val lspPath: String
    val lspUseLatest: Boolean
    val styLuaPath: String
    val sourcemapGenerationCommand: String
    val runStyLua: RunStyluaOption
    val robloxSecurityLevel: RobloxSecurityLevel
    val customDefinitionsPaths: List<String>

    val shouldGenerateSourcemap: Boolean
        get() = isLspEnabled && sourcemapGenerationCommand.isNotBlank()

    // Used mostly to turn off features that are replaced by LSP, so the check is superfluous and checks that intention was to use LSP
    val isLspEnabled: Boolean
        get() = lspConfigurationType == LspConfigurationType.Auto && !lspVersion.isNullOrEmpty()
                || lspConfigurationType == LspConfigurationType.Manual && !lspPath.isEmpty()
}

enum class LspConfigurationType {
    Disabled,
    Auto,
    Manual,
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
