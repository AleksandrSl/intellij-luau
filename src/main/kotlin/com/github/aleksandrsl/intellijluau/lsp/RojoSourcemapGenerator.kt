package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.cli.RojoCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.showProjectNotification
import com.intellij.execution.process.OSProcessHandler
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.io.path.Path
import kotlin.io.path.exists

const val DEFAULT_ROJO_PROJECT_FILE = "default.project.json"

class RojoSourcemapGenerator(private val project: Project, private val coroutineScope: CoroutineScope) :
    ExternalWatcherSourcemapGenerator(project, coroutineScope) {

    private val rojoCli = RojoCli()
    override val name: String = "Rojo"

    override fun start() {
        coroutineScope.launch {
            if (checkIntegrity()) {
                doStart()
            }
        }
    }

    override fun createProcess(): OSProcessHandler {
        val settings = ProjectSettingsState.getInstance(project)
        return rojoCli.generateSourcemap(project, settings.lspRojoProjectFile, settings.lspSourcemapFile)
    }

    // Check that the files configured exist. It's easier than trying to get that process failed because of this.
    // If it fails anyway there will be a different notification.
    private suspend fun checkIntegrity(): Boolean {
        val settings = ProjectSettingsState.getInstance(project)

        val version = withContext(Dispatchers.IO) {
            try {
                RojoCli.queryVersion()
            } catch (e: Exception) {
                null
            }
        }
        if (version == null) {
            SourcemapGenerator.notifications().showProjectNotification(
                LuauBundle.message("luau.sourcemap.generation.rojo.title"),
                "Sourcemap generation requires Rojo installed globally. Please install it or configure sourcemap generation to use a different tool.",
                NotificationType.INFORMATION,
                project
            ) {
                addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.open.settings")) {
                    ShowSettingsUtil.getInstance()
                        .showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                })
            }
            return false
        }

        val root = project.basePath ?: return false
        if (!Path(root).resolve(settings.lspRojoProjectFile).exists()) {
            SourcemapGenerator.notifications().showProjectNotification(
                LuauBundle.message("luau.sourcemap.generation.rojo.title"),
                "Project file (${settings.lspRojoProjectFile}) doesn't exist. Please configure which file to use in the settings",
                NotificationType.INFORMATION,
                project
            ) {
                addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.open.settings")) {
                    ShowSettingsUtil.getInstance()
                        .showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                })
            }
            return false
        }
        return true
    }

    companion object {
        suspend fun canUseRojo(project: Project): Boolean {
            val root = project.basePath ?: return false
            if (!Path(root).resolve(DEFAULT_ROJO_PROJECT_FILE).exists()) {
                return false
            }

            val version = withContext(Dispatchers.IO) {
                try {
                    RojoCli.queryVersion()
                } catch (e: Exception) {
                    null
                }
            }

            return version != null
        }
    }
}
