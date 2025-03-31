package com.github.aleksandrsl.intellijluau.actions

import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.cli.LuauCliService
import com.github.aleksandrsl.intellijluau.cli.StyLuaCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.EDT
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.ide.progress.withBackgroundProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    // TODO (AleksandrSl 14/07/2024): Wouldn't it be better to use BGT?
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project: Project = event.project ?: return

        val projectService = project.service<LuauCliService>()
        val notificationGroupManager = LuauNotifications.pluginNotifications()
        projectService.coroutineScope.launch(Dispatchers.EDT) {
            // Why is not modal progress working?
            // Do I need different contexts here?
            withBackgroundProgress(project, "Stylua format current document") {
                when (val result = StyLuaCli(getToolPath(project).toPath()).formatDocument(project)) {
                    is StyLuaCli.FormatResult.Success -> notificationGroupManager.createNotification(
                        "File formatted",
                        NotificationType.INFORMATION
                    )
                        .notify(project);
                    is StyLuaCli.FormatResult.StyluaError -> notificationGroupManager.createNotification(
                        result.msg,
                        NotificationType.ERROR
                    )
                        .notify(project);
                    null -> null
                }
            }
        }
    }


    companion object {
        // TODO: Do I really need this of this is an old rudiment?
        const val ID =
            "com.github.aleksandrsl.intellijluau.actions.LuauExternalFormatAction" // must stay in-sync with `plugin.xml`

        // I added this function here, because I used prettier plugin as a reference.
        // Might make sense to reconsider, and call StyLua from on save directly.
        fun processVirtualFiles(project: Project, files: Collection<VirtualFile>) {
            val tool = File(ProjectSettingsState.getInstance(project).styLuaPath)
            StyLuaCli(tool.toPath()).formatFiles(project, files)
        }
    }
}
