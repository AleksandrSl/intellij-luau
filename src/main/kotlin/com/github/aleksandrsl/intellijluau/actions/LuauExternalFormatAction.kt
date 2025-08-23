package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.tools.LuauCliService
import com.github.aleksandrsl.intellijluau.tools.StyLuaCli
import com.github.aleksandrsl.intellijluau.tools.ToolchainResolver
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.EDT
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.platform.ide.progress.withBackgroundProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

private val LOG = logger<LuauExternalFormatAction>()

class LuauExternalFormatAction : AnAction() {

    private fun getToolPath(project: Project) = File(ProjectSettingsState.getInstance(project).styLuaPath)

    override fun update(event: AnActionEvent) {
        super.update(event)
        val project = event.project ?: return
        val tool = getToolPath(project)
        event.presentation.isEnabled = tool.exists()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project: Project = event.project ?: return

        val projectService = LuauCliService.getInstance(project)
        val notificationGroupManager = LuauNotifications.pluginNotifications()
        projectService.coroutineScope.launch(Dispatchers.IO) {
            // Why isn't modal progress working?
            // Do I need different contexts here?
            withBackgroundProgress(project, "Stylua format current document") {
                val result = ToolchainResolver.resolveStylua(project)?.formatDocument(project)
                withContext(Dispatchers.EDT) {
                    when (result) {
                        is StyLuaCli.FormatResult.Success -> notificationGroupManager.createNotification(
                            "File formatted",
                            NotificationType.INFORMATION
                        )
                            .notify(project)

                        is StyLuaCli.FormatResult.StyluaError -> notificationGroupManager.createNotification(
                            result.msg,
                            NotificationType.ERROR
                        )
                            .notify(project)

                        null -> null
                    }
                }
            }
        }
    }


    companion object {
        // TODO: Do I really need this of this is an old rudiment?
        const val ID =
            "com.github.aleksandrsl.intellijluau.actions.LuauExternalFormatAction" // must stay in-sync with `plugin.xml`
    }
}
