package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.lsp.DEFAULT_ROJO_PROJECT_FILE
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*
import com.intellij.openapi.diagnostic.logger
import com.intellij.util.xmlb.XmlSerializerUtil

private val LOG = logger<LuauDefaultSettingsState>()

@Service(Service.Level.APP)
@State(
    name = "luauDefaultSettings",
    storages = [Storage(StoragePathMacros.NON_ROAMABLE_FILE)]
)
class LuauDefaultSettingsState :
    PersistentStateComponent<ShareableProjectSettingsState> {
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
        internalState.styLuaPath = state.styLuaPath
        internalState.lspSourcemapGenerationCommand = state.lspSourcemapGenerationCommand
        internalState.runStyLua = state.runStyLua
        internalState.robloxSecurityLevel = state.robloxSecurityLevel
        internalState.customDefinitionsPaths = state.customDefinitionsPaths
        val propertiesComponent = PropertiesComponent.getInstance()
        propertiesComponent.setValue("defaultSettingsSet", true)
    }

    val hasDefaultSettings
        get() = PropertiesComponent.getInstance().getBoolean("defaultSettingsSet", false)

    companion object {
        fun getInstance(): LuauDefaultSettingsState =
            ApplicationManager.getApplication().getService(LuauDefaultSettingsState::class.java)
    }
}

data class DefaultState(
    override var lspVersion: String = Version.Latest.toString(),
    override var lspConfigurationType: LspConfigurationType = LspConfigurationType.Auto,
    override var lspPath: String = "",
    override val lspSourcemapSupportEnabled: Boolean = true,
    override val lspSourcemapGenerationType: LspSourcemapGenerationType = LspSourcemapGenerationType.Rojo,
    override val lspSourcemapFile: String? = "sourcemap.json",
    override val lspRojoProjectFile: String? = DEFAULT_ROJO_PROJECT_FILE,
    override val lspSourcemapGenerationUseIdeaWatcher: Boolean = false,

    override var styLuaPath: String = "",
    override var lspSourcemapGenerationCommand: String = "",
    override var runStyLua: RunStyluaOption = RunStyluaOption.Disabled,
    override var robloxSecurityLevel: RobloxSecurityLevel = defaultRobloxSecurityLevel,
    override var customDefinitionsPaths: List<String> = listOf(),
    override val useLuauExtension: Boolean = true,
) : ShareableProjectSettingsState
