package com.github.aleksandrsl.intellijluau.settings

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
    SerializablePersistentStateComponent<DefaultState>(DefaultState()) {

//    fun save(newState: ShareableProjectSettingsState) {
//        setState(newState)
//        val propertiesComponent = PropertiesComponent.getInstance()
//        propertiesComponent.setValue("defaultSettingsSet", true)
//    }

    fun save(newState: MyState) {
//        setState(DefaultState(
//            lspUseLatest = newState.lspUseLatest,
//            lspVersion = newState.lspVersion,
//            lspConfigurationType = newState.lspConfigurationType,
//            lspPath = newState.lspPath,
//            styLuaPath = newState.styLuaPath,
//            sourcemapGenerationCommand = newState.sourcemapGenerationCommand,
//            runStyLua = newState.runStyLua,
//            robloxSecurityLevel = newState.robloxSecurityLevel,
//            customDefinitionsPaths = newState.customDefinitionsPaths,
//        ))
//        val propertiesComponent = PropertiesComponent.getInstance()
//        propertiesComponent.setValue("defaultSettingsSet", true)
    }

    val hasDefaultSettings
        get() = PropertiesComponent.getInstance().getBoolean("defaultSettingsSet", false)

    companion object {
        fun getInstance(): LuauDefaultSettingsState =
            ApplicationManager.getApplication().service()
    }
}

data class DefaultState(
    override val lspUseLatest: Boolean = true,
    override val lspVersion: String? = null,
    override val lspConfigurationType: LspConfigurationType = LspConfigurationType.Auto,
    override val lspPath: String = "",
    override val styLuaPath: String = "",
    override val sourcemapGenerationCommand: String = "",
    override val runStyLua: RunStyluaOption = RunStyluaOption.Disabled,
    override val robloxSecurityLevel: RobloxSecurityLevel = defaultRobloxSecurityLevel,
    override val customDefinitionsPaths: List<String> = listOf(),
) : ShareableProjectSettingsState
