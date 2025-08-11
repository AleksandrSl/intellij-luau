package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager.CheckLspResult.*
import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager.Companion.robloxApiDocsPath
import com.github.aleksandrsl.intellijluau.settings.*
import com.github.aleksandrsl.intellijluau.util.Version
import com.google.gson.JsonSyntaxException
import com.intellij.ide.BrowserUtil
import com.intellij.ide.util.PropertiesComponent
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.EDT
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.application.PluginPathManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.util.io.toNioPathOrNull
import com.intellij.platform.ide.progress.withBackgroundProgress
import com.intellij.util.concurrency.annotations.RequiresBackgroundThread
import com.intellij.util.download.DownloadableFileService
import com.intellij.util.io.HttpRequests
import com.intellij.util.io.ZipUtil
import com.intellij.util.messages.Topic
import com.intellij.util.system.CpuArch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

private val LOG = logger<LuauLspManager>()
private const val LSP_REPO = "JohnnyMorganz/luau-lsp"
private const val LSP_GITHUB_API_URL = "https://api.github.com/repos/$LSP_REPO/releases"
private const val LSP_DOWNLOAD_BASE_URL = "https://github.com/$LSP_REPO/releases/download"
private const val LSP_RELEASE_NOTES_BASE_URL = "https://github.com/$LSP_REPO/releases/tag"
private const val USER_AGENT = "IntelliJ Luau Plugin (https://github.com/AleksandrSl/intellij-luau)"
private const val LATEST_INSTALLED_LSP_VERSION_KEY = "com.github.aleksandrsl.intellijluau.latestInstalledLspVersion"
private const val API_DOCS = "https://luau-lsp.pages.dev/api-docs/en-us.json"
private const val API_DOCS_JSON = "api-docs.json"

@Suppress("PropertyName")
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

    private var cachedVersionsList: List<Version.Semantic>? = null
    private var cacheExpirationTimeMs: Long = 0L

    // TODO (AleksandrSl 05/06/2025): Check, do I block request if they start concurrently?
    private val cacheMutex = Mutex() // To ensure thread-safe access to cache variables
    private val cacheDurationMs = 25.minutes.inWholeMilliseconds
    private val robloxApiUpdateChannel = Channel<Unit>(Channel.UNLIMITED)

    init {
        coroutineScope.launch {
            for (_request in robloxApiUpdateChannel) {
                try {
                    internalDownloadRobloxApiDefinitionsAndDocs()
                } catch (e: Error) {
                    LOG.error("Failed to update Roblox API definitions and docs", e)
                }
            }
        }
    }

    private suspend fun getVersionsAvailableForDownloadFromApi(project: Project?): List<Version.Semantic> {
        return try {
            val response = withContext(Dispatchers.IO) {
                HttpRequests.request(LSP_GITHUB_API_URL).accept("application/vnd.github.v3+json").userAgent(USER_AGENT)
                    // There are default timeouts in the library that make sense, so I don't have to declare my own.
                    // TODO (AleksandrSl 23/05/2025):
                    // There is a way to get the indicator from the parent coroutine,
                    // but I'm fine with the indeterminate progress bar for now.
                    .readString()
            }
            val releases: List<GitHubRelease> = json.decodeFromString(response)
            releases.filterNot { it.draft || it.prerelease }.mapNotNull {
                // Who knows what format the tags may have, let's be safe and parse what we know
                try {
                    Version.Semantic.parse(it.name)
                } catch (err: Exception) {
                    LOG.warn("Failed to parse LSP GitHub release version: ${it.name}", err)
                    null
                }
            }
        } catch (e: IOException) {
            LuauNotifications.pluginNotifications().createNotification(
                LuauBundle.message("luau.notification.content.could.not.reach.lsp.github.releases"),
                NotificationType.WARNING
            ).notify(project)
            return listOf()
        } catch (e: JsonSyntaxException) {
            LuauNotifications.pluginNotifications().createNotification(
                LuauBundle.message("luau.notification.content.bad.lsp.github.releases"), NotificationType.WARNING
            ).notify(project)
            return listOf()
        }
    }

    // Generated by Gemini 2.5 Pro, it looks reasonable to me to the extent I can verify asynchronous code.
    suspend fun getVersionsAvailableForDownload(project: Project?): List<Version.Semantic> {
        var currentVersions = cachedVersionsList // Read volatile reads once
        val currentTime = System.currentTimeMillis()

        if (currentVersions != null && currentTime < cacheExpirationTimeMs) {
            LOG.debug("Returning custom cached LSP versions.")
            return currentVersions
        }

        // Cache is invalid or expired, need to fetch.
        // Use mutex to ensure only one fetch operation happens if multiple coroutines call this concurrently.
        return cacheMutex.withLock {
            // Double-check after acquiring the lock, another coroutine might have updated the cache.
            currentVersions = cachedVersionsList
            if (currentVersions != null && System.currentTimeMillis() < cacheExpirationTimeMs) {
                LOG.debug("Returning custom cached LSP versions (after lock).")
                return@withLock currentVersions
            }

            LOG.debug("Custom cache expired or empty. Fetching new LSP versions from API.")
            val newVersions = getVersionsAvailableForDownloadFromApi(project)
            cachedVersionsList = newVersions
            cacheExpirationTimeMs = System.currentTimeMillis() + cacheDurationMs
            LOG.debug("LSP versions cache updated. Expiration: $cacheExpirationTimeMs")
            newVersions
        }
    }

    private suspend fun downloadLspWithNotification(version: Version.Semantic, project: Project): Path? {
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

    internal fun getExecutableForVersion(version: Version.Semantic): Path? {
        val executablePath = path(version).resolve(getExecutableName())
        LOG.debug("Getting executable for version: $version, $executablePath")
        if (executablePath.toFile().exists() && executablePath.toFile().canExecute()) {
            return executablePath
        }
        return null
    }

    internal fun getGlobalTypesOrBuiltin(securityLevel: RobloxSecurityLevel): Path? {
        return getGlobalTypes(securityLevel).takeIf { it.exists() } ?: PluginPathManager.getPluginResource(
            javaClass, "typeDeclarations/globalTypes.${securityLevel.name}.d.luau"
        )?.toPath()
    }

    internal fun getGlobalTypes(securityLevel: RobloxSecurityLevel): Path {
        return robloxGlobalTypesPath.resolve("globalTypes.${securityLevel}.d.luau")
    }

    fun downloadRobloxApiDefinitionsAndDocs() {
        robloxApiUpdateChannel.trySend(Unit)
    }

    private suspend fun internalDownloadRobloxApiDefinitionsAndDocs(): DownloadResult {
        try {
            val service = DownloadableFileService.getInstance()
            val descriptions = RobloxSecurityLevel.entries.map {
                val name = "globalTypes.$it.d.luau"
                service.createFileDescription(
                    "https://luau-lsp.pages.dev/type-definitions/$name", name
                )
            }.toMutableList().plus(service.createFileDescription(API_DOCS, API_DOCS_JSON))

            val downloader = service.createDownloader(descriptions, LuauBundle.message("luau.lsp.downloading"))
            val downloadDirectory = downloadPath().toFile()
            withContext(Dispatchers.IO) {
                val downloadResults = downloader.download(downloadDirectory)
                val destination = robloxGlobalTypesPath

                for (result in downloadResults) {
                    val file = result.first
                    file.copyTo(destination.resolve(file.name).toFile(), overwrite = true)
                    file.delete()
                }
            }
            LuauConfigurationCacheService.getInstance().updateLastApiDefinitionsUpdateTime()
            ApplicationManager.getApplication().messageBus.syncPublisher(TOPIC)
                .settingsChanged(LspManagerChangedEvent.ApiDefinitionsUpdated)
            return DownloadResult.Ok(robloxGlobalTypesPath)
        } catch (e: Exception) {
            return DownloadResult.Failed(e.message)
        }
    }

    /**
     * Check for updates once a day or if the files are missing
     */
    private fun checkAndUpdateRobloxApiDefinitionsAndDocsIfNeeded(securityLevel: RobloxSecurityLevel) {
        val lastUpdateTime = LuauConfigurationCacheService.getInstance().state.lastApiDefinitionsUpdateTime
        val missingDeclarations = !getGlobalTypes(securityLevel).exists()
        val missingDocs = !robloxApiDocsPath.exists()
        if (missingDeclarations || missingDocs || System.currentTimeMillis() - lastUpdateTime > API_DEFINITIONS_UPDATE_INTERVAL_MS) {
            downloadRobloxApiDefinitionsAndDocs()
        }
    }

    private fun downloadLspSynchronously(version: Version.Semantic): DownloadResult {
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
            val descriptions = listOf(service.createFileDescription(lspDownloadUrl, "luau-lsp-$version.zip"))
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
                } else {
                    LOG.warn("Unknown download url: ${result.second.downloadUrl}")
                }
            }

            LOG.info("Successfully downloaded LSP to $destination")
            ApplicationManager.getApplication().messageBus.syncPublisher(TOPIC)
                .settingsChanged(LspManagerChangedEvent.NewLspVersionDownloaded(version))
            return DownloadResult.Ok(destination)
        } catch (e: Exception) {
            return DownloadResult.Failed(e.message)
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
        data class UpdateCache(val version: Version.Semantic) : CheckLspResult()
        data object ReadyToUse : CheckLspResult()
        data object LspIsNotConfigured : CheckLspResult()
    }

    // Temporary path to store the downloaded files before they are moved to the target directory.
    private fun downloadPath(): Path = Paths.get(PathManager.getTempPath())

    // Get a directory where a specific version of LSP lies
    private fun path(version: Version.Semantic): Path = lspStorageDirPath.resolve(versionToDirName(version))

    private fun versionToDirName(version: Version.Semantic): String =
        "${version.major}_${version.minor}_${version.patch}"

    sealed class DownloadResult {
        class Ok(val baseDir: Path) : DownloadResult()
        class AlreadyExists(val baseDir: Path) : DownloadResult()
        class Failed(val message: String?) : DownloadResult()
    }

    // I want to check that LSP binary is downloaded according to the settings. If the binary is not there, I want to show the notification.
// I want to check it only once before the LSP is started the first time or a project is open (in this case, it would be good to know it's a luau project).
    fun checkLsp(project: Project) {
        val settings = ProjectSettingsState.getInstance(project)
        if (settings.state.platformType == PlatformType.Roblox) {
            coroutineScope.launch {
                checkAndUpdateRobloxApiDefinitionsAndDocsIfNeeded(ProjectSettingsState.getInstance(project).robloxSecurityLevel)
            }
        }
        if (settings.lspConfigurationType != LspConfigurationType.Auto) return
        coroutineScope.launch(Dispatchers.IO) {
            val checkResult = withBackgroundProgress(project, LuauBundle.message("luau.lsp.check")) {
                val versionsForDownload = getVersionsAvailableForDownload(project)
                val installedVersions = getInstalledVersions()
                return@withBackgroundProgress checkLsp(
                    settings.lspVersion,
                    installedVersions = installedVersions,
                    versionsAvailableForDownload = versionsForDownload
                )
            }
            LOG.debug("Check LSP result: $checkResult")
            // TODO (AleksandrSl 17/05/2025): If I open several projects and get an update notification in every one. Can I update in one and then just close the rest? Looks like it's not straightforward at all.
            when (checkResult) {
                is BinaryMissing -> {
                    LuauNotifications.pluginNotifications().createNotification(
                        LuauBundle.message("luau.lsp.binary.missing.title"), NotificationType.WARNING
                    ).addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.lsp.download")) {
                        coroutineScope.launch {
                            withBackgroundProgress(project, LuauBundle.message("luau.lsp.downloading")) {
                                if (downloadLspWithNotification(checkResult.version, project) != null) {
                                    checkLsp(project)
                                }
                            }
                        }
                    })
                        .addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.open.settings")) {
                            ShowSettingsUtil.getInstance()
                                .showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                        }).apply { isSuggestionType = true }.notify(project)
                }

                LspIsNotConfigured -> {
                    LuauNotifications.pluginNotifications().createNotification(
                        LuauBundle.message("luau.lsp.not.configured.title"),
                        LuauBundle.message("luau.lsp.not.configured.content"),
                        NotificationType.INFORMATION
                    )
                        .addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.open.settings")) {
                            ShowSettingsUtil.getInstance()
                                .showSettingsDialog(project, ProjectSettingsConfigurable::class.java)
                        }).notify(project)
                }

                ReadyToUse -> { // All good, maybe I want to return true or something
                }

                is UpdateAvailable -> {
                    LuauNotifications.pluginNotifications().createNotification(
                        LuauBundle.message("luau.lsp.update.available.title", checkResult.version.toString()),
                        NotificationType.INFORMATION
                    ).addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.lsp.update")) {
                        coroutineScope.launch {
                            withBackgroundProgress(project, LuauBundle.message("luau.lsp.downloading")) {
                                if (downloadLspWithNotification(checkResult.version, project) != null) {
                                    checkLsp(project)
                                }
                            }
                        }
                    })
//                        There is also BrowseNotificationAction, not sure if it's the same
                        .addAction(NotificationAction.createSimple(LuauBundle.message("luau.lsp.update.available.release.notes")) {
                            BrowserUtil.browse("$LSP_RELEASE_NOTES_BASE_URL/${checkResult.version}")
                        }).apply { isSuggestionType = true }.notify(project)
                }

                is UpdateCache -> updateLatestInstalledVersionCache(checkResult.version)
            }
        }
    }

    interface LspManagerChangeListener {
        fun settingsChanged(event: LspManagerChangedEvent)
    }

    sealed class LspManagerChangedEvent() {
        data class NewLspVersionDownloaded(val version: Version.Semantic) : LspManagerChangedEvent()
        data object ApiDefinitionsUpdated : LspManagerChangedEvent()
    }

    companion object {
        @JvmStatic
        fun getInstance(): LuauLspManager = service()

        private val API_DEFINITIONS_UPDATE_INTERVAL_MS = 1.days.inWholeMilliseconds

        internal fun updateLatestInstalledVersionCache(version: Version.Semantic) {
            latestInstalledLspVersionCache = version
        }

        @Topic.AppLevel
        val TOPIC = Topic.create(
            "LSP manager updates", LspManagerChangeListener::class.java
        )

        fun checkLsp(
            currentVersion: Version,
            installedVersions: List<Version.Semantic>,
            versionsAvailableForDownload: List<Version.Semantic>,
        ): CheckLspResult {
            return when (currentVersion) {
                is Version.Latest -> {
                    val latestVersion = versionsAvailableForDownload.max()
                    if (installedVersions.isEmpty()) {
                        BinaryMissing(latestVersion)
                    } else {
                        val latestInstalledVersion = installedVersions.max()
                        if (latestVersion > latestInstalledVersion) UpdateAvailable(latestVersion) else {
                            if (latestInstalledVersion != latestInstalledLspVersionCache) UpdateCache(
                                latestInstalledVersion
                            ) else ReadyToUse
                        }
                    }
                }

                is Version.Semantic -> if (installedVersions.contains(currentVersion)) ReadyToUse
                else BinaryMissing(currentVersion)
            }
        }

        internal var latestInstalledLspVersionCache: Version.Semantic?
            get() = PropertiesComponent.getInstance().getValue(LATEST_INSTALLED_LSP_VERSION_KEY)
                ?.let { Version.Semantic.parse(it) }
            private set(value) {
                PropertiesComponent.getInstance().setValue(LATEST_INSTALLED_LSP_VERSION_KEY, value.toString())
            }


        @RequiresBackgroundThread
        fun getInstalledVersions(): List<Version.Semantic> {
            return try {
                lspStorageDirPath.toFile().list()?.mapNotNull {
                    try {
                        dirNameToVersion(it)
                    } catch (e: IllegalArgumentException) {
                        // Well, macOS adds the DS_Store folder in the directory,
                        // who knows what else we may have, let's ignore errors parsing the name
                        null
                    }
                }?.sorted() ?: emptyList()
            } catch (e: Exception) {
                LOG.error("Failed to get LSP versions from disk. Basepath: ${lspStorageDirPath}. Error:", e)
                emptyList()
            }
        }

        @RequiresBackgroundThread
        internal fun getLatestInstalledLspVersion(): Version.Semantic? {
            return getInstalledVersions().maxOrNull()?.also {
                latestInstalledLspVersionCache = it
            }
        }

        val basePath: Path
            // As per https://platform.jetbrains.com/t/is-there-a-special-place-where-plugin-can-store-binaries-that-are-shared-between-different-ides/2120 the config dir should be copied when the IDE is updated.
            get() = PathManager.getConfigDir().resolve("intellij-luau")

        // Directory with all the LSPs
        val lspStorageDirPath: Path
            get() = basePath.resolve("lsp")

        val robloxApiDocsPath: Path
            get() = robloxGlobalTypesPath.resolve(API_DOCS_JSON)

        private val robloxGlobalTypesPath: Path
            get() = basePath.resolve("roblox").resolve("globalTypes")
    }
}

private fun dirNameToVersion(dirName: String): Version.Semantic {
    return try {
        val parts = dirName.split('_')
        Version.Semantic(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
    } catch (e: Exception) {
        throw IllegalArgumentException("Invalid directory name: $dirName", e)
    }
}

@RequiresBackgroundThread
fun Project.getLspConfiguration(): LspConfiguration {
    val settings = ProjectSettingsState.getInstance(this)
    return when (settings.lspConfigurationType) {
        LspConfigurationType.Auto -> LspConfiguration.Auto(this)
        LspConfigurationType.Manual -> LspConfiguration.Manual(this)
        LspConfigurationType.Disabled -> LspConfiguration.Disabled
    }
}

// Lightweight hack to get the LSP version for the Auto configuration.
// The cache should be populated 99% of the time.
// Can be removed when LSP versions with server info are highly adopted.
fun Project.getAutoLspVersion(): Version.Semantic? {
    val settings = ProjectSettingsState.getInstance(this)
    if (settings.lspConfigurationType == LspConfigurationType.Auto) {
        return settings.lspVersion.let { it as? Version.Semantic ?: LuauLspManager.latestInstalledLspVersionCache }
    }
    return null
}

fun Project.getGlobalTypesOrBuiltin(): Path? {
    val manager = LuauLspManager.getInstance()
    val settings = ProjectSettingsState.getInstance(this)
    return manager.getGlobalTypesOrBuiltin(settings.robloxSecurityLevel)
}

sealed class LspConfiguration() {
    // Escape hatch to run LspCli for a non-saved setting.
    class ForSettings(
        project: Project, override val executablePath: Path?, override val isReady: Boolean
    ) : Enabled(project)

    class Manual(project: Project) : Enabled(project) {
        override val executablePath: Path?
            get() {
                return settings.lspPath.toNioPathOrNull()
            }
        override val isReady: Boolean = true
    }

    class Auto(project: Project) : Enabled(project) {
        // The cache should be set when LSP is downloaded the first time,
        // after that I assume that if LSP is missing, it's an error, so we should try to run it and throw.
        val version: Version.Semantic? = settings.lspVersion.let {
            it as? Version.Semantic ?: LuauLspManager.getLatestInstalledLspVersion()
        }

        override val isReady: Boolean
            get() = version != null
        override val executablePath: Path?
            get() = version?.let { LuauLspManager.getInstance().getExecutableForVersion(it) }
    }

    sealed class Enabled(val project: Project) : LspConfiguration() {
        abstract val executablePath: Path?
        val definitions: List<Path>
            get() = customDeclarations.toMutableList().apply {
                if (settings.state.platformType == PlatformType.Roblox) {
                    project.getGlobalTypesOrBuiltin()?.also {
                        add(it)
                    }
                }
            }
        val docs: List<Path>
            get() = if (settings.state.platformType == PlatformType.Roblox) listOf(robloxApiDocsPath) else emptyList()
        abstract val isReady: Boolean
        private val customDeclarations: List<Path>
            get() = settings.customDefinitionsPaths.mapNotNull { it.toNioPathOrNull() }
        protected val settings
            get() = ProjectSettingsState.getInstance(project)
    }

    data object Disabled : LspConfiguration()
}
