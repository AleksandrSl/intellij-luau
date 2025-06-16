package com.github.aleksandrsl.intellijluau.lsp

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*

@Service(Service.Level.APP)
@State(
    name = "LuauDeclarationCache", storages = [Storage(StoragePathMacros.CACHE_FILE)]
)
class LuauConfigurationCacheService : PersistentStateComponent<LuauConfigurationCacheService.State> {

    data class State(
        var lastApiDefinitionsUpdateTime: Long = 0,
    )

    private var state = State()

    override fun getState(): State = state
    override fun loadState(state: State) {
        this.state = state
    }

    companion object {
        fun getInstance(): LuauConfigurationCacheService = ApplicationManager.getApplication().service()
    }

    fun updateLastApiDefinitionsUpdateTime() {
        state.lastApiDefinitionsUpdateTime = System.currentTimeMillis()
    }
}
