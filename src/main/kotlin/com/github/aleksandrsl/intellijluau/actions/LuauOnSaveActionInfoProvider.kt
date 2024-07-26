package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable.Companion.CONFIGURABLE_ID
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.ide.actionsOnSave.ActionOnSaveBackedByOwnConfigurable
import com.intellij.ide.actionsOnSave.ActionOnSaveContext
import com.intellij.ide.actionsOnSave.ActionOnSaveInfo
import com.intellij.ide.actionsOnSave.ActionOnSaveInfoProvider

class LuauOnSaveActionInfoProvider : ActionOnSaveInfoProvider() {
    override fun getActionOnSaveInfos(context: ActionOnSaveContext):
            List<ActionOnSaveInfo> = listOf(
        LuauExternalFormatOnSaveActionInfo(context),
    )

    override fun getSearchableOptions(): Collection<String> {
        return listOf(
            LuauBundle.message("luau.run.on.save.checkbox.on.actions.on.save.page"),
        )
    }
}


// TODO (AleksandrSl 30/06/2024): Implement showing version of stylua in the comments
private class LuauExternalFormatOnSaveActionInfo(actionOnSaveContext: ActionOnSaveContext) :
    ActionOnSaveBackedByOwnConfigurable<ProjectSettingsConfigurable>(
        actionOnSaveContext,
        CONFIGURABLE_ID,
        ProjectSettingsConfigurable::class.java
    ) {

    override fun getActionOnSaveName() = LuauBundle.message("luau.run.on.save.checkbox.on.actions.on.save.page")

    override fun isActionOnSaveEnabledAccordingToStoredState(): Boolean = ProjectSettingsState.instance.runStyLuaOnSave

    override fun isActionOnSaveEnabledAccordingToUiState(configurable: ProjectSettingsConfigurable): Boolean =
        configurable.component?.runStyLuaOnSave ?: false

    override fun setActionOnSaveEnabled(configurable: ProjectSettingsConfigurable, enabled: Boolean) {
        configurable.component?.runStyLuaOnSave = enabled
    }

    // Quite useless since I don't have a special configuration page for stylua
    override fun getActionLinks() = listOf(createGoToPageInSettingsLink(CONFIGURABLE_ID))
}
