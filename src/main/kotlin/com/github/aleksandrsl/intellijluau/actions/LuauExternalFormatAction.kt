package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.cli.LuauCliService
import com.github.aleksandrsl.intellijluau.cli.StyLuaCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.platform.ide.progress.withBackgroundProgress
import kotlinx.coroutines.launch
import java.io.File

private val LOG = logger<LuauExternalFormatAction>()

class LuauExternalFormatAction : AnAction() {

    private var tool = File(ProjectSettingsState.instance.styLuaPath)

    override fun update(event: AnActionEvent) {
        super.update(event)
        tool = File(ProjectSettingsState.instance.styLuaPath)
        event.presentation.isEnabled = tool.exists()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project: Project = event.project ?: return

        val projectService = project.service<LuauCliService>()
        val notificationGroupManager = NotificationGroupManager.getInstance()
            .getNotificationGroup("Luau notifications")
        projectService.coroutineScope.launch {
            // Why is not modal progress working?
            // Do I need different contexts here?
            withBackgroundProgress(project, "Stylua format current document") {
                val output = StyLuaCli(tool.toPath()).formatDocumentOnDisk(project)
                if (output == null) {
                    notificationGroupManager.createNotification("Failed to run stylua", NotificationType.ERROR)
                        .notify(project);
                    return@withBackgroundProgress
                }
                if (output.checkSuccess(LOG)) {
                    notificationGroupManager.createNotification("File formatted", NotificationType.INFORMATION)
                        .notify(project);
                }
            }
        }
    }

    // TODO: Do I really need this of this is an old rudiment?
    companion object {
        const val ID = "com.github.aleksandrsl.intellijluau.actions.LuauExternalFormatAction" // must stay in-sync with `plugin.xml`
    }
}
