package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.lsp.DEFAULT_ROJO_PROJECT_FILE
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

// TODO (AleksandrSl 11/05/2025): Try using - SerializablePersistentStateComponent.
//  https://plugins.jetbrains.com/docs/intellij/persisting-state-of-components.html#implementing-the-persistentstatecomponent-interface
// TODO (AleksandrSl 24/05/2025): I tried and it doesn't work, settings are not saved to the disk at all.
//  Maybe it's this issue - https://youtrack.jetbrains.com/issue/IJPL-188400/SerializablePersistentStateComponent-is-not-persisted.
//  Check again when it's resolved.
@Service(Service.Level.PROJECT)
@State(name = "LuauPluginSettings", storages = [Storage("luauPlugin.xml")])
class ProjectSettingsState : PersistentStateComponent<ProjectSettingsState.State> {
    private var internalState: State = State()

    var lspVersion: Version
        get() = Version.parse(internalState.lspVersion)
        set(value) {
            internalState.lspVersion = value.toString()
        }

    val lspConfigurationType: LspConfigurationType
        get() = internalState.lspConfigurationType

    val lspPath: String
        get() = internalState.lspPath

    val styLuaPath: String
        get() = internalState.styLuaPath

    val lspSourcemapGenerationCommand: String
        get() = internalState.lspSourcemapGenerationCommand

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

    /**
     * Indicates whether the LSP support is enabled
     * and minimally configured (at least some path or version is provided).
     */
    val isLspEnabledAndMinimallyConfigured
        get() = internalState.isLspEnabledAndMinimallyConfigured

    val useLuauExtension
        get() = internalState.useLuauExtension

    val lspRojoProjectFile
        get() = internalState.lspRojoProjectFile

    val lspSourcemapGenerationType
        get() = internalState.lspSourcemapGenerationType

    val lspSourcemapSupportEnabled
        get() = internalState.lspSourcemapSupportEnabled

    val lspSourcemapGenerationUseIdeaWatcher
        get() = internalState.lspSourcemapGenerationUseIdeaWatcher

    val lspSourcemapFile
        get() = internalState.lspSourcemapFile

    override fun getState(): State {
        return internalState
    }

    override fun loadState(state: State) {
        XmlSerializerUtil.copyBean(state, internalState)
    }

    fun enableRojoSourcemapGeneration() {
        internalState.lspSourcemapGenerationType = LspSourcemapGenerationType.Rojo
    }

    fun loadDefaultSettings() {
        val defaults = LuauDefaultSettingsState.getInstance().state
        internalState.lspConfigurationType = defaults.lspConfigurationType
        internalState.styLuaPath = defaults.styLuaPath
        internalState.lspPath = defaults.lspPath

        internalState.lspVersion = defaults.lspVersion
        internalState.lspSourcemapGenerationCommand = defaults.lspSourcemapGenerationCommand
        internalState.runStyLua = defaults.runStyLua
        internalState.robloxSecurityLevel = defaults.robloxSecurityLevel
        internalState.customDefinitionsPaths = defaults.customDefinitionsPaths
        internalState.useLuauExtension = defaults.useLuauExtension
    }

    data class State(
        override var lspVersion: String = Version.Latest.toString(),
        override var lspConfigurationType: LspConfigurationType = LspConfigurationType.Auto,
        override var lspPath: String = "",
        override var lspSourcemapSupportEnabled: Boolean = true,
        override var lspSourcemapGenerationType: LspSourcemapGenerationType = LspSourcemapGenerationType.Disabled,
        override var lspSourcemapGenerationUseIdeaWatcher: Boolean = false,
        override var lspSourcemapGenerationCommand: String = "",
        override var lspSourcemapFile: String = "sourcemap.json",
        override var lspRojoProjectFile: String = DEFAULT_ROJO_PROJECT_FILE,

        override var styLuaPath: String = "",
        override var runStyLua: RunStyluaOption = RunStyluaOption.Disabled,
        override var robloxSecurityLevel: RobloxSecurityLevel = defaultRobloxSecurityLevel,
        override var customDefinitionsPaths: List<String> = listOf(),
        override var useLuauExtension: Boolean = true,
    ) : ShareableProjectSettingsState

    companion object {
        fun getInstance(project: Project): ProjectSettingsState = project.getService(ProjectSettingsState::class.java)
    }
}

interface ShareableProjectSettingsState {
    val lspConfigurationType: LspConfigurationType
    val lspVersion: String
    val lspPath: String
    val lspSourcemapSupportEnabled: Boolean
    val lspSourcemapGenerationType: LspSourcemapGenerationType

    // TODO (AleksandrSl 24/05/2025): Support luau-lsp.sourcemap.includeNonScripts:
    //  Whether to include non script instances in the sourcemap.
    //  May be disabled for expensive DataModels later.
    val lspSourcemapFile: String?
    val lspRojoProjectFile: String?
    val lspSourcemapGenerationUseIdeaWatcher: Boolean
    val lspSourcemapGenerationCommand: String

    val styLuaPath: String
    val runStyLua: RunStyluaOption
    val robloxSecurityLevel: RobloxSecurityLevel
    val customDefinitionsPaths: List<String>
    val useLuauExtension: Boolean

    // Used mostly to turn off features that are replaced by LSP, so the check is superfluous and checks that intention was to use LSP
    val isLspEnabledAndMinimallyConfigured: Boolean
        get() = lspConfigurationType == LspConfigurationType.Auto || lspConfigurationType == LspConfigurationType.Manual && !lspPath.isEmpty()
}

enum class LspSourcemapGenerationType {
    Disabled, Rojo, Manual
}

enum class LspConfigurationType {
    Disabled, Auto, Manual,
}

enum class RobloxSecurityLevel {
    None, LocalUserSecurity, PluginSecurity, RobloxScriptSecurity,
}

val defaultRobloxSecurityLevel = RobloxSecurityLevel.None

enum class RunStyluaOption {
    Disabled, RunOnSave, RunOnSaveAndDisableBuiltinFormatter, RunInsteadOfFormatter,
}
