package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauIcons
import com.github.aleksandrsl.intellijluau.lsp.SourcemapGeneratorService
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project

class RestartSourcemapGeneratorAction :
    DumbAwareAction(LuauBundle.message("luau.action.restart.sourcemap.generator.text"), null, LuauIcons.FILE) {
    override fun actionPerformed(event: AnActionEvent) {
        val project: Project = event.project ?: return
        SourcemapGeneratorService.getInstance(project).restart()
    }

    override fun update(event: AnActionEvent) {
        super.update(event)
        event.presentation.isEnabled =
            event.project?.let { SourcemapGeneratorService.getInstance(it).canRestart } ?: false
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
