package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.cli.StyLuaCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.ide.actionsOnSave.impl.ActionsOnSaveFileDocumentManagerListener
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import java.io.File

private val LOG = logger<LuauExternalFormatOnSaveAction>()

class LuauExternalFormatOnSaveAction : ActionsOnSaveFileDocumentManagerListener.DocumentUpdatingActionOnSave() {
    override fun isEnabledForProject(project: Project): Boolean {
        return ProjectSettingsState.getInstance(project).shouldRunStyLuaOnSave
    }

    override val presentableName: String
        get() = "Stylua"

    override suspend fun updateDocument(project: Project, document: Document) {
        val manager = FileDocumentManager.getInstance()
        val file = manager.getFile(document)
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
        val tool = File(ProjectSettingsState.getInstance(project).styLuaPath)
        val result = StyLuaCli(tool.toPath()).formatFile(project, file)
        if (result is StyLuaCli.FormatResult.StyluaError) {
            LuauNotifications
                .pluginNotifications()
                .createNotification("StyLua Error", result.msg, NotificationType.ERROR)
                .addAction(NotificationAction.createSimple("Open settings") {
                    ShowSettingsUtil.getInstance().showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                })
                .notify(project)
        }
    }
}
