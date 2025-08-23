package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.tools.StyLuaCli
import com.github.aleksandrsl.intellijluau.tools.ToolchainResolver
import com.intellij.ide.actionsOnSave.impl.ActionsOnSaveFileDocumentManagerListener
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project

private val LOG = logger<LuauExternalFormatOnSaveAction>()

class LuauExternalFormatOnSaveAction : ActionsOnSaveFileDocumentManagerListener.DocumentUpdatingActionOnSave() {
    override fun isEnabledForProject(project: Project): Boolean {
        return ProjectSettingsState.getInstance(project).runStyluaOnSave
    }

    override val presentableName: String
        get() = "Stylua"

    override suspend fun updateDocument(project: Project, document: Document) {
        // Prettier does this in read action, but getFile doesn't require it.
        val manager = FileDocumentManager.getInstance()
        val file = manager.getFile(document)
        // Prettier also check that config is saved. Maybe I have to do it as well?
        // I spend around 20 minutes reading code of other on save implementations but I didn't understand what post format processor has to do with it.
        // TODO (AleksandrSl 29/06/2024): Deal with this later when I'll do runOnReformat
//            if (file != null && prettierConfiguration.isRunOnReformat()) {
//                val onSaveOptions = FormatOnSaveOptions.getInstance(project)
//                if (onSaveOptions.isRunOnSaveEnabled && onSaveOptions.isFileTypeSelected(file.fileType)) {
//                    // already processed as com.intellij.prettierjs.PrettierPostFormatProcessor
//                    return@mapNotNull null
//                }
//            }
        if (file?.fileType != LuauFileType) return
        LOG.debug("Processing $file on save")
        val stylua = ToolchainResolver.resolveStylua(project) ?: return
        val result = stylua.formatFile(project, file)

        // TODO (AleksandrSl 11/04/2025): From prettier
        //       val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return@writeCommandAction
        //      if (!editor.isDisposed && editor.virtualFile == file && formattingDiff.cursorOffset >= 0) {
        //        editor.caretModel.moveToOffset(formattingDiff.cursorOffset)
        //      }
        //   Do they return cursor to the same place it was before? Should I do that or it works fine automatiacally. Have to check
        //   In my case there is also a weird jumping of the cursor, though if I use reformat with stylua with StyluaFormatterService I don't have this problem.
        if (result is StyLuaCli.FormatResult.StyluaError) {
            LuauNotifications
                .pluginNotifications()
                .createNotification("StyLua error", result.msg, NotificationType.ERROR)
                .addAction(NotificationAction.createSimple("Open settings") {
                    ShowSettingsUtil.getInstance().showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                })
                .notify(project)
        }
    }
}
