package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.settings.LspConfigurationType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.settings.RobloxSecurityLevel
import com.github.aleksandrsl.intellijluau.util.Version
import com.google.gson.JsonSyntaxException
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.EDT
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.application.PluginPathManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.util.io.toNioPathOrNull
import com.intellij.platform.ide.progress.withBackgroundProgress
import com.intellij.util.download.DownloadableFileService
import com.intellij.util.io.HttpRequests
import com.intellij.util.io.ZipUtil
import com.intellij.util.system.CpuArch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path

private val LOG = logger<LuauLspManager>()
private const val LSP_REPO = "JohnnyMorganz/luau-lsp"
private const val LSP_GITHUB_API_URL = "https://api.github.com/repos/$LSP_REPO/releases"
private const val LSP_DOWNLOAD_BASE_URL = "https://github.com/$LSP_REPO/releases/download"
private const val LSP_RELEASE_NOTES_BASE_URL = "https://github.com/$LSP_REPO/releases/tag"
private const val USER_AGENT = "IntelliJ Luau Plugin (https://github.com/AleksandrSl/intellij-luau)"

@Serializable
data class GitHubRelease(
    val tag_name: String, val name: String, val prerelease: Boolean, val draft: Boolean, val published_at: String
)

@Service(Service.Level.APP)
class LuauLspManager(private val coroutineScope: CoroutineScope) {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    suspend fun getVersionsAvailableForDownload(): List<Version.Semantic> {
        return try {
            val response = withContext(Dispatchers.IO) {
                HttpRequests.request(LSP_GITHUB_API_URL).accept("application/vnd.github.v3+json").userAgent(USER_AGENT)
                    .readString(ProgressManager.getInstance().progressIndicator)
            }
            val releases: List<GitHubRelease> = json.decodeFromString(response)
            releases.filterNot { it.draft || it.prerelease }.map { Version.Semantic.parse(it.name) }
        } catch (e: IOException) {
            LuauNotifications.pluginNotifications().createNotification(
                LuauBundle.message("luau.notification.content.could.not.reach.lsp.github.releases"),
                NotificationType.WARNING
            )
            return listOf()
        } catch (e: JsonSyntaxException) {
            LuauNotifications.pluginNotifications().createNotification(
                LuauBundle.message("luau.notification.content.bad.lsp.github.releases"), NotificationType.WARNING
            )
            return listOf()
        }
    }

    suspend fun downloadLspWithNotification(version: Version.Semantic, project: Project): Path? {
        val result = downloadLspSynchronously(version)
        return withContext(Dispatchers.EDT) {
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

    suspend fun downloadLsp(version: Version.Semantic): DownloadResult {
        return withContext(Dispatchers.IO) {
            return@withContext downloadLspSynchronously(version)
        }
    }

    fun getExecutableForVersion(version: Version.Semantic): Path? {
        val executablePath = path(version).resolve(getExecutableName())
        LOG.debug("Getting executable for version: $version, $executablePath")
        if (executablePath.toFile().exists() && executablePath.toFile().canExecute()) {
            return executablePath
        }
        return null
    }

    fun getGlobalTypesForVersion(version: Version.Semantic, securityLevel: RobloxSecurityLevel): Path {
        return path(version).resolve("globalTypes.${securityLevel}.d.luau")
    }

    fun downloadLspSynchronously(version: Version.Semantic): DownloadResult {
        if (getInstalledVersions().contains(version)) {
            return DownloadResult.AlreadyExists(path(version))
        }
        try {
            val lspDownloadUrl = when {
                SystemInfo.isWindows -> "$LSP_DOWNLOAD_BASE_URL/$version/luau-lsp-win64.zip"
                // TODO (AleksandrSl 10/05/2025): Check older macs and m1 support?
                SystemInfo.isMac -> "$LSP_DOWNLOAD_BASE_URL/$version/luau-lsp-macos.zip"
                SystemInfo.isLinux && CpuArch.isArm64() -> "$LSP_DOWNLOAD_BASE_URL/$version/luau-lsp-linux-arm64.zip"
                SystemInfo.isLinux -> "$LSP_DOWNLOAD_BASE_URL/$version/luau-lsp-linux.zip"
                else -> throw UnsupportedOperationException("Unsupported operating system")
            }

            LOG.info("Downloading LSP from $lspDownloadUrl")
            val service = DownloadableFileService.getInstance()
            val declarationDescriptors = RobloxSecurityLevel.entries.map {
                val name = "globalTypes.$it.d.luau"
                service.createFileDescription(
                    "https://raw.githubusercontent.com/$LSP_REPO/$version/scripts/$name", name
                )
            }

            val descriptions = listOf(
                service.createFileDescription(lspDownloadUrl, "luau-lsp-$version.zip"),
            ).plus(declarationDescriptors)

            val downloader = service.createDownloader(descriptions, LuauBundle.message("luau.lsp.downloading"))
            val downloadDirectory = downloadPath().toFile()
            val downloadResults = downloader.download(downloadDirectory)
            val destination = path(version)

            for (result in downloadResults) {
                if (result.second.downloadUrl == lspDownloadUrl) {
                    val archiveFile = result.first
                    ZipUtil.extract(Path(archiveFile.path), destination, null)
                    archiveFile.delete()

                    // Make the file executable on Unix systems
                    if (!SystemInfo.isWindows) {
                        destination.resolve(getExecutableName()).toFile().setExecutable(true)
                    }
                } else if (result.second in declarationDescriptors) {
                    val file = result.first
                    file.copyTo(destination.resolve(file.name).toFile(), overwrite = true)
                    file.delete()
                } else {
                    LOG.warn("Unknown download url: ${result.second.downloadUrl}")
                }
            }

            LOG.info("Successfully downloaded LSP to $destination")
            return DownloadResult.Ok(destination)
        } catch (e: Exception) {
            LOG.error("Failed to download LSP", e)
            return DownloadResult.Failed(e.message)
        }
    }

    fun getInstalledVersions(): List<Version.Semantic> {
        return try {
            basePath().toFile().list()?.mapNotNull { dirNameToVersion(it) }?.sorted() ?: emptyList()
        } catch (e: Exception) {
            LOG.error("Failed to get LSP versions. Basepath: ${basePath()}. Error:", e)
            emptyList()
        }
    }

    private fun getExecutableName(): String {
        return when {
            SystemInfo.isWindows -> "luau-lsp.exe"
            else -> "luau-lsp"
        }
    }

    sealed class CheckLspResult {
        data class UpdateAvailable(val version: Version.Semantic) : CheckLspResult()
        data class BinaryMissing(val version: Version.Semantic) : CheckLspResult()
        data class UpdateSettings(val version: Version.Semantic) : CheckLspResult()
        data object ReadyToUse : CheckLspResult()
        data object LspIsNotConfigured : CheckLspResult()
    }

    private fun downloadPath(): Path = Paths.get(PathManager.getTempPath())
    private fun basePath() = Paths.get(PathManager.getSystemPath()).resolve("intellij-luau").resolve("lsp")
    private fun path(version: Version.Semantic) = basePath().resolve(versionToDirName(version))

    private fun versionToDirName(version: Version.Semantic): String =
        "${version.major}_${version.minor}_${version.patch}"

    private fun dirNameToVersion(dirName: String): Version.Semantic {
        return try {
            val parts = dirName.split('_')
            Version.Semantic(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid directory name: $dirName", e)
        }
    }

    sealed class DownloadResult {
        class Ok(val baseDir: Path) : DownloadResult()
        class AlreadyExists(val baseDir: Path) : DownloadResult()
        class Failed(val message: String?) : DownloadResult()
    }

    // I want to check that LSP binary is downloaded according to the settings. If the binary is not there, I want to show the notification.
    // I want to check it only once before the LSP is started the first time or a project is open (in this case, it would be good to know it's a luau project).
    fun checkLsp(project: Project) {
        val settings = ProjectSettingsState.getInstance(project)
        if (settings.lspConfigurationType != LspConfigurationType.Auto) return
        coroutineScope.launch(Dispatchers.IO) {
            val checkResult = withBackgroundProgress(project, LuauBundle.message("luau.lsp.check")) {
                val currentVersion = settings.lspVersion
                val versionsForDownload = getVersionsAvailableForDownload()
                val installedVersions = getInstalledVersions()
                return@withBackgroundProgress checkLsp(
                    currentVersion,
                    settings.lspUseLatest,
                    installedVersions = installedVersions,
                    versionsAvailableForDownload = versionsForDownload
                )
            }
            LOG.debug("Check LSP result: $checkResult")
            // TODO (AleksandrSl 17/05/2025): If I open several projects and get an update notification in every one. Can I update in one and then just close the rest.
            when (checkResult) {
                is CheckLspResult.BinaryMissing -> {
                    LuauNotifications.pluginNotifications().createNotification(
                        LuauBundle.message("luau.lsp.binary.missing.title"), NotificationType.INFORMATION
                    ).addAction(NotificationAction.createSimple(LuauBundle.message("luau.lsp.download")) {
                        coroutineScope.launch {
                            withBackgroundProgress(project, LuauBundle.message("luau.lsp.downloading")) {
                                downloadLspWithNotification(checkResult.version, project)
                                settings.lspVersion = checkResult.version
                                restartLspServerAsync(project)
                            }
                        }
                    })
                        .addAction(NotificationAction.createSimple(LuauBundle.message("luau.notification.actions.open.settings")) {
                            ShowSettingsUtil.getInstance()
                                .showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                        }).notify(project)
                }

                CheckLspResult.LspIsNotConfigured -> {
                    LuauNotifications.pluginNotifications().createNotification(
                        LuauBundle.message("luau.lsp.not.configured.title"),
                        LuauBundle.message("luau.lsp.not.configured.content"),
                        NotificationType.INFORMATION
                    )
                        .addAction(NotificationAction.createSimple(LuauBundle.message("luau.notification.actions.open.settings")) {
                            ShowSettingsUtil.getInstance()
                                .showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                        }).notify(project)
                }

                CheckLspResult.ReadyToUse -> { // All good, maybe I want to return true or something
                }

                is CheckLspResult.UpdateAvailable -> {
                    LuauNotifications.pluginNotifications().createNotification(
                        LuauBundle.message("luau.lsp.update.available.title"), LuauBundle.message(
                            "luau.lsp.update.available.content",
                            checkResult.version.toString(),
                            "$LSP_RELEASE_NOTES_BASE_URL/${checkResult.version}"
                        ), NotificationType.INFORMATION
                    ).addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.lsp.update")) {
                        coroutineScope.launch {
                            withBackgroundProgress(project, LuauBundle.message("luau.lsp.downloading")) {
                                if (downloadLspWithNotification(checkResult.version, project) != null) {
                                    // It will be good to propagate these changes to other projects.
                                    settings.lspVersion = checkResult.version
                                    restartLspServerAsync(project)
                                }
                            }
                        }
                    }).notify(project)
                }

                is CheckLspResult.UpdateSettings -> {
                    settings.lspVersion = checkResult.version
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(): LuauLspManager = service()

        fun checkLsp(
            currentVersion: Version.Semantic?,
            lspUseLatest: Boolean,
            installedVersions: List<Version.Semantic>,
            versionsAvailableForDownload: List<Version.Semantic>,
        ): CheckLspResult {
            if (lspUseLatest) {
                val latestVersion = versionsAvailableForDownload.max()
                if (latestVersion != currentVersion) {
                    // Currently used LSP version is not the latest,
                    // but maybe we have the binary and the project settings have to be updated.
                    if (installedVersions.contains(latestVersion)) {
                        return CheckLspResult.UpdateSettings(latestVersion)
                    }
                    if (currentVersion == null) {
                        return CheckLspResult.BinaryMissing(latestVersion)
                    }
                    return CheckLspResult.UpdateAvailable(latestVersion)
                }
                return CheckLspResult.ReadyToUse
            } else {
                if (currentVersion == null) {
                    return CheckLspResult.LspIsNotConfigured
                }
                return if (installedVersions.contains(currentVersion)) CheckLspResult.ReadyToUse
                else CheckLspResult.BinaryMissing(currentVersion)
            }
        }
    }
}

fun Project.getLspConfiguration(): LspConfiguration {
    val settings = ProjectSettingsState.getInstance(this)
    return when (settings.lspConfigurationType) {
        LspConfigurationType.Auto -> LspConfiguration.Auto(this)
        LspConfigurationType.Manual -> LspConfiguration.Manual(this)
        LspConfigurationType.Disabled -> LspConfiguration.Disabled
    }
}

sealed class LspConfiguration() {
    // Escape hatch to run LspCli for a non-saved setting.
    class ForSettings(
        project: Project,
        override val executablePath: Path?,
        override val definitions: List<Path>,
        override val isReady: Boolean
    ) : Enabled(project)

    class Manual(project: Project) : Enabled(project) {
        override val executablePath: Path?
            get() {
                return ProjectSettingsState.getInstance(project).lspPath.toNioPathOrNull()
            }
        override val definitions: List<Path>
            get() {
                val settings = ProjectSettingsState.getInstance(project)
                val typesPath = PluginPathManager.getPluginResource(
                    javaClass, "typeDeclarations/globalTypes.${settings.robloxSecurityLevel.name}.d.luau"
                )?.toPath()
                if (typesPath === null) {
                    return customDeclarations
                }
                return customDeclarations.plusElement(typesPath)
            }
        override val isReady: Boolean = true
    }

    class Auto(project: Project) : Enabled(project) {
        // Will be set when LSP is downloaded the first time, after that I assume that if LSP is missing, it's an error so we should try to run it and throw.
        val version: Version.Semantic? = ProjectSettingsState.getInstance(project).lspVersion

        override val isReady: Boolean
            get() = version != null
        override val executablePath: Path?
            get() = version?.let { LuauLspManager.getInstance().getExecutableForVersion(it) }
        override val definitions: List<Path>
            get() = version?.let {
                val manager = LuauLspManager.getInstance()
                val settings = ProjectSettingsState.getInstance(project)
                customDeclarations.plusElement(manager.getGlobalTypesForVersion(it, settings.robloxSecurityLevel))
            } ?: listOf()
    }

    sealed class Enabled(val project: Project) : LspConfiguration() {
        abstract val executablePath: Path?
        abstract val definitions: List<Path>
        abstract val isReady: Boolean
        internal val customDeclarations: List<Path>
            get() = ProjectSettingsState.getInstance(project).customDefinitionsPaths.mapNotNull { it.toNioPathOrNull() }
    }

    object Disabled : LspConfiguration()
}
