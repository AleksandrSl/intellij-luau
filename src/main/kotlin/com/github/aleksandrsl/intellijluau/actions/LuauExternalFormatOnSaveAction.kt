package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.ide.actionsOnSave.impl.ActionsOnSaveFileDocumentManagerListener
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.util.containers.ContainerUtil

private val LOG = logger<LuauExternalFormatOnSaveAction>()

// TODO (AleksandrSl 30/03/2025):
//  Looks I need a DocumentUpdatingActionOnSave
//  Note: If the Action on Save is going to update file contents, for example,
//  to format a file on save, then extend DocumentUpdatingActionOnSave.
//  Unlike ActionOnSave.processDocuments, which assumes modal progress,
//  DocumentUpdatingActionOnSave.updateDocument runs with cancelable background progress.
class LuauExternalFormatOnSaveAction : ActionsOnSaveFileDocumentManagerListener.ActionOnSave() {
    override fun isEnabledForProject(project: Project): Boolean {
        return ProjectSettingsState.getInstance(project).runStyLuaOnSave
    }

    override fun processDocuments(project: Project, documents: Array<Document>) {
        // prettier checks that action is enabled, but it's done by the code calling this action
        val manager = FileDocumentManager.getInstance()
        val files = ContainerUtil.mapNotNull(
            documents
        ) { document: Document ->
            val file = manager.getFile(
                document
            )
            // TODO (AleksandrSl 29/06/2024): Deal with this later when I'll do runOnReformat
//            if (file != null && prettierConfiguration.isRunOnReformat()) {
//                val onSaveOptions = FormatOnSaveOptions.getInstance(project)
//                if (onSaveOptions.isRunOnSaveEnabled && onSaveOptions.isFileTypeSelected(file.fileType)) {
//                    // already processed as com.intellij.prettierjs.PrettierPostFormatProcessor
//                    return@mapNotNull null
//                }
//            }
            file
        }.filter { it.fileType == LuauFileType }


        if (files.isNotEmpty()) {
            LOG.debug("Processing ${files.joinToString(", ") { it.path }} on save")
            LuauExternalFormatAction.processVirtualFiles(
                project,
                files,
            )
        }
    }
}
