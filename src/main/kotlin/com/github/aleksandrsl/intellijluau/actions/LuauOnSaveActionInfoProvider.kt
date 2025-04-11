package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable.Companion.CONFIGURABLE_ID
import com.intellij.ide.actionsOnSave.*

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
// It's not very useful currently, since I have radio buttons on the main settings page and they are hard to manipulate from outside reliably.
// Given I have 3 mutually exclusive options it's better to configure them on the main page, but I'll leave this one for visibility that such settings exist.
private class LuauExternalFormatOnSaveActionInfo(actionOnSaveContext: ActionOnSaveContext) :
    ActionOnSaveBackedByOwnConfigurable<ProjectSettingsConfigurable>(
        actionOnSaveContext,
        CONFIGURABLE_ID,
        ProjectSettingsConfigurable::class.java
    ) {

    override fun getActionOnSaveName(): String {
        return LuauBundle.message("luau.run.on.save.checkbox.on.actions.on.save.page")
    }

    override fun setActionOnSaveEnabled(configurable: ProjectSettingsConfigurable, enabled: Boolean) {
        // Intentionally do nothing.
    }

    private fun _getComment() =
        ActionOnSaveComment.info(LuauBundle.message("luau.run.on.save.comment.on.actions.on.save.page"))

    override fun getCommentAccordingToStoredState(): ActionOnSaveComment {
        return _getComment()
    }

    override fun getCommentAccordingToUiState(configurable: ProjectSettingsConfigurable): ActionOnSaveComment? {
        return _getComment()
    }

    override fun isApplicableAccordingToStoredState(): Boolean = false

    override fun isApplicableAccordingToUiState(configurable: ProjectSettingsConfigurable): Boolean = false

    // Quite useless since I don't have a special configuration page for stylua
    override fun getActionLinks() = listOf(createGoToPageInSettingsLink(CONFIGURABLE_ID))
}
