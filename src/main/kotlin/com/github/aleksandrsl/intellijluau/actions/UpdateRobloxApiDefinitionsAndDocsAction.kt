package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauIcons
import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class UpdateRobloxApiDefinitionsAndDocsAction :
    DumbAwareAction(LuauBundle.message("luau.action.update.roblox.api.definitions"), null, LuauIcons.ROBLOX) {
    override fun actionPerformed(event: AnActionEvent) {
        LuauLspManager.getInstance().downloadRobloxApiDefinitionsAndDocs()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
