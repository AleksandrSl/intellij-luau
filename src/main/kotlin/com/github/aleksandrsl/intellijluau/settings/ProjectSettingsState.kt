package com.github.aleksandrsl.intellijluau.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.messages.Topic
import com.intellij.util.xmlb.XmlSerializerUtil
import kotlin.reflect.KProperty1

@Service(Service.Level.PROJECT)
@State(name = "LuauPluginSettings", storages = [Storage("luauPlugin.xml")])
internal class ProjectSettingsState(private val project: Project) :
    PersistentStateComponent<ProjectSettingsState.State> {
    private var internalState: State = State()

    val lspPath: String
        get() = internalState.lspPath

    val styLuaPath: String
        get() = internalState.styLuaPath

    val robloxCliPath: String
        get() = internalState.robloxCliPath

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

    val generateSourceMapsFromRbxp
        get() = internalState.generateSourceMapsFromRbxp

    val rbxpForSourcemapPath
        get() = internalState.rbxpForSourcemapPath

    val shouldGenerateSourceMapsFromRbxp
        get() = internalState.shouldGenerateSourceMapsFromRbxp


    override fun getState(): State {
        return internalState
    }

    override fun loadState(state: State) {
        XmlSerializerUtil.copyBean(state, internalState)
    }

    fun update(block: State.() -> Unit) {
        val oldState = internalState
        val newState = internalState.copy()
        block(newState)
        internalState = newState
        notifySettingsChanged(SettingsChangedEvent(oldState, newState))
    }

    internal data class State(
        var lspPath: String = "",
        var styLuaPath: String = "",
        var robloxCliPath: String = "",
        var runStyLua: RunStyluaOption = RunStyluaOption.Disabled,
        var robloxSecurityLevel: RobloxSecurityLevel = defaultRobloxSecurityLevel,
        var customDefinitionsPaths: List<String> = listOf(),
        var generateSourceMapsFromRbxp: Boolean = true,
        var rbxpForSourcemapPath: String = ""
    ) {
        val shouldGenerateSourceMapsFromRbxp: Boolean
            get() = rbxpForSourcemapPath.isNotBlank() && generateSourceMapsFromRbxp && robloxCliPath.isNotBlank()

    }

    companion object {
        fun getInstance(project: Project): ProjectSettingsState = project.getService(ProjectSettingsState::class.java)
        val TOPIC = Topic.create(
            "Luau settings changes",
            SettingsChangeListener::class.java,
            Topic.BroadcastDirection.TO_PARENT
        )
    }

    interface SettingsChangeListener {
        fun settingsChanged(e: SettingsChangedEvent)
    }

    private fun notifySettingsChanged(event: SettingsChangedEvent) {
        project.messageBus.syncPublisher(TOPIC).settingsChanged(event)
    }

    class SettingsChangedEvent(val oldState: State, val newState: State) {
        /** Use it like `event.isChanged(State::foo)` to check whether `foo` property is changed or not */
        fun isChanged(prop: KProperty1<State, *>): Boolean = prop.get(oldState) != prop.get(newState)
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
