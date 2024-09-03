package com.github.aleksandrsl.intellijluau.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@Service(Service.Level.PROJECT)
@State(name = "LuauPluginSettings", storages = [Storage("luauPlugin.xml")])
internal class ProjectSettingsState : PersistentStateComponent<ProjectSettingsState> {
    var lspPath: String = ""
    var styLuaPath: String = ""
    var runStyLuaOnSave: Boolean = false
    var robloxSecurityLevel: RobloxSecurityLevel = defaultRobloxSecurityLevel
    var customDefinitionsPath: String = ""

    override fun getState(): ProjectSettingsState {
        return this
    }

    override fun loadState(state: ProjectSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
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
    Custom
}

val defaultRobloxSecurityLevel = RobloxSecurityLevel.None
