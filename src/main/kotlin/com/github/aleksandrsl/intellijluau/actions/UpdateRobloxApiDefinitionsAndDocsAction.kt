package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauIcons
import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project

class UpdateRobloxApiDefinitionsAndDocsAction :
// TODO (AleksandrSl 16/06/2025): Get roblox icon
    DumbAwareAction(LuauBundle.message("luau.action.update.roblox.api.definitions"), null, LuauIcons.FILE) {
    override fun actionPerformed(event: AnActionEvent) {
        val project: Project = event.project ?: return
        LuauLspManager.getInstance().downloadRobloxApiDefinitionsAndDocs(project)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
