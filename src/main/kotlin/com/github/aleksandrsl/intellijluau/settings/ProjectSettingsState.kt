package com.github.aleksandrsl.intellijluau.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@Service
@State(name = "LuauPluginSettings", storages = [Storage("luauPlugin.xml")])
internal class ProjectSettingsState : PersistentStateComponent<ProjectSettingsState> {
    var lspPath: String = ""
    var styLuaPath: String = ""

    override fun getState(): ProjectSettingsState {
        return this
    }

    override fun loadState(state: ProjectSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: ProjectSettingsState
            get() = ApplicationManager.getApplication().getService(ProjectSettingsState::class.java)
    }
}
