package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import kotlinx.serialization.Serializable
import kotlin.text.isNullOrEmpty

@Service(Service.Level.PROJECT)
@State(name = "LuauPluginSettings", storages = [Storage("luauPlugin.xml")])
class ProjectSettingsState : SerializablePersistentStateComponent<MyState>(MyState()) {

    var lspVersion: Version.Semantic?
        get() {
            val version = state.lspVersion
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
            updateState {
                it.copy(lspVersion = value?.toString())
            }
        }

    var lspConfigurationType: LspConfigurationType
        get() = state.lspConfigurationType
        set(value) {
            updateState { it.copy(lspConfigurationType = value) }
        }

    var lspPath: String
        get() = state.lspPath
        set(value) {
            updateState { it.copy(lspPath = value) }
        }

    var styLuaPath: String
        get() = state.styLuaPath
        set(value) {
            updateState { it.copy(styLuaPath = value) }
        }

    var sourcemapGenerationCommand: String
        get() = state.sourcemapGenerationCommand
        // Seems like internal doesn't limit the mutability to the same package 😿
        internal set(value) {
            updateState { it.copy(sourcemapGenerationCommand = value) }
        }

    val runStyluaOnSave
        get() = runStyLua == RunStyluaOption.RunOnSave || runStyLua == RunStyluaOption.RunOnSaveAndDisableBuiltinFormatter

    val disableBuiltinFormatter
        get() = runStyLua == RunStyluaOption.RunOnSaveAndDisableBuiltinFormatter

    val runStyLuaInsteadOfFormatter
        get() = runStyLua == RunStyluaOption.RunInsteadOfFormatter

    private val runStyLua
        get() = if (state.styLuaPath.isNotBlank()) {
            state.runStyLua
        } else {
            RunStyluaOption.Disabled
        }

    internal var runStyLuaRaw
        get() = if (state.styLuaPath.isNotBlank()) {
            state.runStyLua
        } else {
            RunStyluaOption.Disabled
        }
        set(value) {
            updateState { it.copy(runStyLua = value) }
        }

    var robloxSecurityLevel
        get() = state.robloxSecurityLevel
        set(value) {
            updateState { it.copy(robloxSecurityLevel = value) }
        }

    var customDefinitionsPaths
        get() = state.customDefinitionsPaths
        set(value) {
            updateState { it.copy(customDefinitionsPaths = value) }
        }

    val shouldGenerateSourcemap
        get() = isLspEnabled && sourcemapGenerationCommand.isNotBlank()

    val isLspEnabled
        get() = lspConfigurationType == LspConfigurationType.Auto && !state.lspVersion.isNullOrEmpty() || lspConfigurationType == LspConfigurationType.Manual && !lspPath.isEmpty()

    var lspUseLatest
        get() = state.lspUseLatest
        set(value) {
            updateState { it.copy(lspUseLatest = value) }
        }

    fun loadDefaultSettings() {
        val defaults = LuauDefaultSettingsState.getInstance().state
        updateState {
            it.copy(
                lspUseLatest = defaults.lspUseLatest,
                lspConfigurationType = defaults.lspConfigurationType,
                styLuaPath = defaults.styLuaPath,
                lspVersion = defaults.lspVersion,
                lspPath = defaults.lspPath,
                sourcemapGenerationCommand = defaults.sourcemapGenerationCommand,
                runStyLua = defaults.runStyLua,
                robloxSecurityLevel = defaults.robloxSecurityLevel,
                customDefinitionsPaths = defaults.customDefinitionsPaths,
            )
        }
    }

    fun updateLspVersion(version: Version.Semantic?, useLatest: Boolean) {
        updateState {
            it.copy(lspVersion = version?.toString(), lspUseLatest = useLatest)
        }
    }

    companion object {
        fun getInstance(project: Project): ProjectSettingsState = project.service()
    }
}

data class MyState(
    @JvmField val lspUseLatest: Boolean = true,
    @JvmField val lspVersion: String? = null,
    @JvmField val lspConfigurationType: LspConfigurationType = LspConfigurationType.Auto,
    @JvmField val lspPath: String = "",
    @JvmField val styLuaPath: String = "",
    @JvmField val sourcemapGenerationCommand: String = "",
    @JvmField val runStyLua: RunStyluaOption = RunStyluaOption.Disabled,
    @JvmField val robloxSecurityLevel: RobloxSecurityLevel = defaultRobloxSecurityLevel,
    @JvmField val customDefinitionsPaths: List<String> = listOf(),
)

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
        get() = lspConfigurationType == LspConfigurationType.Auto && !lspVersion.isNullOrEmpty() || lspConfigurationType == LspConfigurationType.Manual && !lspPath.isEmpty()
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
