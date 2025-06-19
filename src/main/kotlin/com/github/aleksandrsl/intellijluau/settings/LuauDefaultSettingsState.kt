package com.github.aleksandrsl.intellijluau.settings

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*
import com.intellij.openapi.diagnostic.logger
import com.intellij.util.xmlb.XmlSerializerUtil

private val LOG = logger<LuauDefaultSettingsState>()

private const val DEFAULT_SETTINGS_SET_KEY = "defaultSettingsSet"

@Service(Service.Level.APP)
@State(
    name = "luauDefaultSettings",
    // TODO (AleksandrSl 19/06/2025): Why did I make it non roamable?
    storages = [Storage(StoragePathMacros.NON_ROAMABLE_FILE)]
)
class LuauDefaultSettingsState : PersistentStateComponent<ShareableProjectSettingsState> {
    private var internalState = DefaultState()

    override fun getState(): ShareableProjectSettingsState {
        return internalState
    }

    override fun loadState(state: ShareableProjectSettingsState) {
        XmlSerializerUtil.copyBean(state, internalState)
    }

    fun save(state: ShareableProjectSettingsState) {
        internalState.lspVersion = state.lspVersion
        internalState.lspConfigurationType = state.lspConfigurationType
        internalState.lspPath = state.lspPath
        internalState.lspSourcemapSupportEnabled = state.lspSourcemapSupportEnabled
        internalState.lspSourcemapGenerationType = state.lspSourcemapGenerationType
        internalState.lspSourcemapGenerationUseIdeaWatcher = state.lspSourcemapGenerationUseIdeaWatcher
        internalState.lspSourcemapGenerationCommand = state.lspSourcemapGenerationCommand
        internalState.lspSourcemapFile = state.lspSourcemapFile
        internalState.lspRojoProjectFile = state.lspRojoProjectFile

        internalState.runStyLua = state.runStyLua
        internalState.robloxSecurityLevel = state.robloxSecurityLevel
        internalState.customDefinitionsPaths = state.customDefinitionsPaths
        val propertiesComponent = PropertiesComponent.getInstance()
        propertiesComponent.setValue(DEFAULT_SETTINGS_SET_KEY, true)
    }

    val hasDefaultSettings
        get() = PropertiesComponent.getInstance().getBoolean(DEFAULT_SETTINGS_SET_KEY, false)

    companion object {
        fun getInstance(): LuauDefaultSettingsState =
            ApplicationManager.getApplication().getService(LuauDefaultSettingsState::class.java)
    }
}

data class DefaultState(
    override var lspVersion: String = ShareableProjectSettingsStateDefaults.lspVersion,
    override var lspConfigurationType: LspConfigurationType = ShareableProjectSettingsStateDefaults.lspConfigurationType,
    override var lspPath: String = ShareableProjectSettingsStateDefaults.lspPath,
    override var lspSourcemapSupportEnabled: Boolean = ShareableProjectSettingsStateDefaults.lspSourcemapSupportEnabled,
    override var lspSourcemapGenerationType: LspSourcemapGenerationType = ShareableProjectSettingsStateDefaults.lspSourcemapGenerationType,
    override var lspSourcemapGenerationUseIdeaWatcher: Boolean = ShareableProjectSettingsStateDefaults.lspSourcemapGenerationUseIdeaWatcher,
    override var lspSourcemapGenerationCommand: String = ShareableProjectSettingsStateDefaults.lspSourcemapGenerationCommand,
    override var lspSourcemapFile: String = ShareableProjectSettingsStateDefaults.lspSourcemapFile,
    override var lspRojoProjectFile: String = ShareableProjectSettingsStateDefaults.lspRojoProjectFile,

    override var styLuaPath: String = ShareableProjectSettingsStateDefaults.styLuaPath,
    override var runStyLua: RunStyluaOption = ShareableProjectSettingsStateDefaults.runStyLua,
    override var robloxSecurityLevel: RobloxSecurityLevel = ShareableProjectSettingsStateDefaults.robloxSecurityLevel,
    override var customDefinitionsPaths: List<String> = ShareableProjectSettingsStateDefaults.customDefinitionsPaths,
    override var useLuauExtension: Boolean = ShareableProjectSettingsStateDefaults.useLuauExtension,
    override var platformType: PlatformType = ShareableProjectSettingsStateDefaults.platformType,
) : ShareableProjectSettingsState
