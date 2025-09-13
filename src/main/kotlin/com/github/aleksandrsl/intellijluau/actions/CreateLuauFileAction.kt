package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauIcons
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory

// Using this constructor helps to add the icon and description to the action
class CreateLuauFileAction : CreateFileFromTemplateAction(
    LuauBundle.messagePointer("luau.action.new.file.name"),
    LuauBundle.messagePointer("luau.action.new.file.description"),
    LuauIcons.FILE
), DumbAware {
    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle(LuauBundle.message("luau.action.new.file.dialog.title"))
            .addKind(
                "Source file",
                LuauIcons.FILE,
                "Luau file"
            )
    }

    // Though already provided in constructor, this method override is required
    override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String =
        LuauBundle.message("luau.action.new.file.name")
}
