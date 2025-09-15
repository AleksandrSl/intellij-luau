package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager.CheckLspResult
import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager.DownloadResult
import com.github.aleksandrsl.intellijluau.settings.LspConfigurationType
import com.github.aleksandrsl.intellijluau.settings.PlatformType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.ide.BrowserUtil
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.EDT
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import com.intellij.platform.ide.progress.withBackgroundProgress
import com.intellij.platform.lsp.api.LspServerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicBoolean

private val LOG = logger<LuauLspProjectService>()

@Suppress("UnstableApiUsage")
@Service(Service.Level.PROJECT)
class LuauLspProjectService(private val project: Project, private val coroutineScope: CoroutineScope) {
    private val isChecking = AtomicBoolean(false)
    private var hasCheckedLsp = false
    private var shouldStartLsp = false

//    todo : Should I somehow reset the check when the settings change?
    // Probably not, since if you've changed the settings to a custom version of the lsp if will be force downloaded.
    // If you turned the lsp off, it is fine to keep the state.
    fun checkLsp(force: Boolean = false): Boolean {
        if ((!hasCheckedLsp || force) && isChecking.compareAndSet(false, true)) {
            coroutineScope.launch {
                try {
                    shouldStartLsp = doCheckLsp()
                } finally {
                    hasCheckedLsp = true
                    isChecking.set(false)
                    if (shouldStartLsp) {
                        LspServerManager.getInstance(project).startServersIfNeeded(LuauLspServerSupportProvider::class.java)
                    }
                }
            }
        }
        return shouldStartLsp
    }

    private suspend fun doCheckLsp(): Boolean {
        val lspManager = LuauLspManager.getInstance()
        val settings = ProjectSettingsState.getInstance(project)
        if (settings.state.platformType == PlatformType.Roblox) {
            lspManager.checkAndUpdateRobloxApiDefinitionsAndDocsIfNeeded(ProjectSettingsState.getInstance(project).robloxSecurityLevel)
        }
        if (settings.lspConfigurationType != LspConfigurationType.Auto) return true

        val checkResult = withBackgroundProgress(project, LuauBundle.message("luau.lsp.check")) {
            lspManager.checkLsp(settings.lspVersion)
        }
        return when (checkResult) {

            is CheckLspResult.BinaryMissing -> {
                LuauNotifications.pluginNotifications().createNotification(
                    LuauBundle.message("luau.lsp.binary.missing.title"), NotificationType.WARNING
                ).addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.lsp.download")) {
                    coroutineScope.launch {
                        withBackgroundProgress(project, LuauBundle.message("luau.lsp.downloading")) {
                            if (downloadLspWithNotification(checkResult.version) != null) {
                                checkLsp(force = true)
                            }
                        }
                    }
                })
                    .addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.open.settings")) {
                        ShowSettingsUtil.getInstance()
                            .showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                    }).apply { isSuggestionType = true }.notify(project)
                false
            }

            CheckLspResult.LspIsNotConfigured -> {
                LuauNotifications.pluginNotifications().createNotification(
                    LuauBundle.message("luau.lsp.not.configured.title"),
                    LuauBundle.message("luau.lsp.not.configured.content"),
                    NotificationType.INFORMATION
                )
                    .addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.open.settings")) {
                        ShowSettingsUtil.getInstance()
                            .showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                    }).notify(project)
                false
            }

            is CheckLspResult.UpdateAvailable -> {
                LuauNotifications.pluginNotifications().createNotification(
                    LuauBundle.message("luau.lsp.update.available.title", checkResult.version.toString()),
                    NotificationType.INFORMATION
                ).addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.lsp.update")) {
                    coroutineScope.launch {
                        withBackgroundProgress(project, LuauBundle.message("luau.lsp.downloading")) {
                            if (downloadLspWithNotification(checkResult.version) != null) {
                                checkLsp(force = true)
                            }
                        }
                    }
                })
                    .addAction(NotificationAction.createSimple(LuauBundle.message("luau.lsp.update.available.release.notes")) {
                        BrowserUtil.browse(LuauLspManager.composeReleaseNotesUrl(checkResult.version))
                    }).apply { isSuggestionType = true }.notify(project)
                true
            }

            CheckLspResult.ReadyToUse -> {
                true
            }
        }
    }

    private suspend fun downloadLspWithNotification(version: Version.Semantic): Path? {
        val lspManager = LuauLspManager.getInstance()
        return withBackgroundProgress(project, LuauBundle.message("luau.lsp.downloading")) {
            val result = lspManager.downloadLsp(version)
            withContext(Dispatchers.EDT) {
                when (result) {
                    // TODO (AleksandrSl 19/05/2025): Join with Ok
                    is DownloadResult.AlreadyExists -> {
                        LuauNotifications.pluginNotifications().createNotification(
                            LuauBundle.message("luau.notification.lsp.download.ok"), NotificationType.INFORMATION
                        ).notify(project)
                        return@withContext result.baseDir
                    }

                    is DownloadResult.Ok -> {
                        LuauNotifications.pluginNotifications().createNotification(
                            LuauBundle.message("luau.notification.lsp.download.ok"), NotificationType.INFORMATION
                        ).notify(project)
                        return@withContext result.baseDir
                    }

                    is DownloadResult.Failed -> {
                        LuauNotifications.pluginNotifications().createNotification(
                            LuauBundle.message("luau.notification.lsp.download.error"), NotificationType.ERROR
                        ).notify(project)
                        return@withContext null
                    }
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun getInstance(project: Project): LuauLspProjectService = project.service()
    }
}
