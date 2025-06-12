package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.cli.LspCli
import com.github.aleksandrsl.intellijluau.cli.RojoCli
import com.github.aleksandrsl.intellijluau.cli.SourcemapGeneratorCli
import com.github.aleksandrsl.intellijluau.lsp.LspConfiguration
import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager
import com.github.aleksandrsl.intellijluau.lsp.restartLspServerAsync
import com.github.aleksandrsl.intellijluau.util.PlatformCompatibility
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.icons.AllIcons
import com.intellij.ide.actions.RevealFileAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.EDT
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.observable.properties.AtomicProperty
import com.intellij.openapi.observable.util.transform
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.io.toNioPathOrNull
import com.intellij.platform.ide.progress.runWithModalProgressBlocking
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBRadioButton
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.layout.and
import com.intellij.ui.layout.not
import com.intellij.ui.layout.selected
import com.intellij.ui.layout.selectedValueIs
import com.intellij.util.ui.AnimatedIcon
import com.intellij.util.ui.AsyncProcessIcon
import com.intellij.util.ui.UIUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.awt.event.ActionEvent
import java.awt.event.ItemEvent
import java.nio.file.Path
import javax.swing.AbstractAction
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JRadioButton
import kotlin.io.path.exists

private val LOG = logger<LuauLspSettings>()

// Important learning, when parent becomes visible it makes all the children visible as well, even if they were explicitly hidden.
// The only escape is to use a predicate or observable
// TODO (AleksandrSl 27/05/2025): Try to use AtomicActions more, since I've switched to them for versions. Now I use it only for the loader.
class LuauLspSettings(
    private val project: com.intellij.openapi.project.Project,
    private val settings: ProjectSettingsState.State,
    private val coroutineScope: CoroutineScope,
) {
    private lateinit var rojoVersionLabel: JLabel
    private lateinit var rojoVersionLoader: AnimatedIcon
    private val rojoVersion = AtomicProperty("")
    private val lspVersionsForDownload = AtomicProperty<VersionsForDownload>(VersionsForDownload.Loading)
    private val lspInstalledVersions = AtomicProperty<InstalledVersions>(InstalledVersions.Loading)

    private lateinit var sourcemapGenerationManulRadio: JBRadioButton
    private lateinit var sourcemapGenerationRojoRadio: JBRadioButton
    private lateinit var sourcemapSupportCheckbox: JBCheckBox
    private lateinit var lspVersionCombobox: LspVersionComboBox
    private lateinit var lspDisabled: JRadioButton
    private lateinit var lspManual: JRadioButton
    private lateinit var lspAuto: JRadioButton
    private lateinit var lspVersionsLoader: AnimatedIcon
    private val lspVersionStateLabelComponent = JBLabel().apply { isVisible = false }
    private val downloadLspButton = JButton().apply { isVisible = false }
    private val lspVersionLabelComponent = JBLabel(if (settings.lspPath.isEmpty()) "No binary specified" else "")

    private val lspVersionBinding = object : MutableProperty<Version?> {
        override fun get(): Version? = if (settings.lspUseLatest) Version.Latest else settings.lspVersion?.let {
            Version.Semantic.parse(it)
        }

        override fun set(value: Version?) {
            if (value == Version.Latest) {
                settings.lspUseLatest = true
                settings.lspVersion = getLatestInstalledVersion()?.toString() ?: Version.Latest.toString()
            } else {
                settings.lspUseLatest = false
                settings.lspVersion = value?.toString()
            }
        }
    }

    private fun getLatestInstalledVersion(): Version.Semantic? {
        val container = lspInstalledVersions.get()
        if (container is InstalledVersions.Loaded) {
            return container.versions.maxOrNull()
        }
        return null
    }

    private fun download(version: Version.Semantic): Boolean {
        val lspManager = LuauLspManager.getInstance()
        return try {
            runWithModalProgressBlocking(project, LuauBundle.message("luau.lsp.downloading")) {
                val result = lspManager.downloadLsp(version)
                when (result) {
                    is LuauLspManager.DownloadResult.Failed -> {
                        displayDownloadError("Failed to download $version: ${result.message}")
                        false
                    }

                    is LuauLspManager.DownloadResult.AlreadyExists, is LuauLspManager.DownloadResult.Ok -> {
                        withContext(Dispatchers.EDT) {
                            lspInstalledVersions.set(lspInstalledVersions.get().let {
                                // Consider getting versions anew?
                                if (it is InstalledVersions.Loaded) {
                                    InstalledVersions.Loaded(it.versions + version)
                                } else {
                                    InstalledVersions.Loaded(listOf(version))
                                }
                            })
                            updateLspVersionActions(lspVersionsForDownload.get(), lspInstalledVersions.get())
                            restartLspServerAsync(project)
                        }
                        true
                    }
                }
            }
        } catch (err: Exception) {
            displayDownloadError("Failed to download $version: ${err.message}")
            false
        }
    }

    /**
     * Updates the label component to display the manually specified LSP version.
     *
     * @param newVersion the new LSP version to display in the label component
     */
    private fun setManualLspVersion(newVersion: String) {
        lspVersionLabelComponent.text = newVersion
    }

    private val lspPathComponent = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor()))
        onExistingFileChanged {
            // I guess this will launch in project scope, so the coroutine will finish even if I close the settings.
            // Not sure if I should about it or not.
            coroutineScope.launch(Dispatchers.IO) {
                setManualLspVersion(
                    LspCli(
                        project, LspConfiguration.ForSettings(project, it, listOf(), true)
                    ).queryVersion().toString()
                )
            }
        }
    }

    private val rojoProjectFileComponent = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(
            TextBrowseFolderListener(
                FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor(),
                // Using a project here and for sourcemap, but not for manual LSP is on purpose. These two are most likely located in your project, while LSP isn't
                project
            )
        )
    }

    private val sourcemapFileComponent = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(
            TextBrowseFolderListener(
                FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor(), project
            )
        )
    }

    private fun showLspDownloadButton(version: Version.Semantic, isUpdate: Boolean) {
        downloadLspButton.action = object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                download(version)
                settings.lspVersion = version.toString()
            }
        }
        downloadLspButton.text = if (isUpdate) "Update to $version" else "Download: $version"
        downloadLspButton.isVisible = true
        lspVersionStateLabelComponent.isVisible = false
    }

    private fun showLspMessage(message: String, isError: Boolean = false) {
        lspVersionStateLabelComponent.foreground = if (isError) {
            UIUtil.getErrorForeground()
        } else {
            UIUtil.getLabelForeground()
        }
        lspVersionStateLabelComponent.text = message
        lspVersionStateLabelComponent.isVisible = true
        downloadLspButton.isVisible = false
    }

    private fun hideLspRelatedActions() {
        downloadLspButton.isVisible = false
        lspVersionStateLabelComponent.isVisible = false
    }

    private fun displayDownloadError(message: String) {
        showLspMessage(message, isError = true)
    }

    private fun updateLspVersionActions(
        versionsForDownload: VersionsForDownload, installedVersions: InstalledVersions
    ) {
        if (!lspAuto.isSelected) {
            return
        }
        if (versionsForDownload is VersionsForDownload.Loading || installedVersions is InstalledVersions.Loading) {
            return
        }
        if (versionsForDownload is VersionsForDownload.Failed) {
            showLspMessage(versionsForDownload.message, isError = true)
            return
        }
        if (installedVersions is InstalledVersions.Failed) {
            showLspMessage(installedVersions.message, isError = true)
            return
        }
        if (versionsForDownload !is VersionsForDownload.Loaded || versionsForDownload.versions.isEmpty() || installedVersions !is InstalledVersions.Loaded) {
            return
        }

        val latestInstalled = getLatestInstalledVersion()
        val isLspVersionModified = lspVersionCombobox.getSelectedVersion() != lspVersionBinding.get()
        val isConfigurationTypeModified =
            lspAuto.isSelected && settings.lspConfigurationType != LspConfigurationType.Auto
        val shouldShowActions = !(isLspVersionModified || isConfigurationTypeModified)
        val selectedVersion = lspVersionCombobox.getSelectedVersion()
        when (val result = LuauLspManager.checkLsp(
            // I don't like this but this is a trick to show the download instead of update if the current version in settings is null.
            (if (isLspVersionModified) selectedVersion.toString() else settings.lspVersion)?.let {
                when (val parsed = Version.parse(it)) {
                    Version.Latest -> latestInstalled
                    is Version.Semantic -> parsed
                }
            },
            selectedVersion == Version.Latest,
            installedVersions = installedVersions.versions,
            versionsAvailableForDownload = versionsForDownload.versions
        )) {
            LuauLspManager.CheckLspResult.LspIsNotConfigured -> showLspMessage("Please select a version")
            is LuauLspManager.CheckLspResult.BinaryMissing -> {
                // TODO (AleksandrSl 15/05/2025):  Show error around the combobox
                // Show download button if the settings are not modified,
                // otherwise lsp will be downloaded upon settings application.
                if (shouldShowActions) {
                    showLspDownloadButton(result.version, isUpdate = false)
                } else {
                    hideLspRelatedActions()
                }
            }

            LuauLspManager.CheckLspResult.ReadyToUse -> {
                if (selectedVersion == Version.Latest) {
                    showLspMessage("Up to date")
                } else {
                    // Do nothing if not using latest; there is no valuable info I can give
                    hideLspRelatedActions()
                }
            }

            is LuauLspManager.CheckLspResult.UpdateAvailable -> {
                if (isLspVersionModified) {
                    showLspDownloadButton(result.version, isUpdate = true)
                } else {
                    hideLspRelatedActions()
                }
            }

            is LuauLspManager.CheckLspResult.UpdateSettings -> {
                // This state should not be possible in settings since I will update the settings for the latest
                // version at the project start and maybe when download is done in a project.
                settings.lspVersion = result.version.toString()
                updateLspVersionActions(versionsForDownload, lspInstalledVersions.get())
            }
        }
    }

    private fun checkRojoVersion() {
        coroutineScope.launch {
            withLoader(rojoVersionLoader) {
                try {
                    val version = withContext(Dispatchers.IO) {
                        RojoCli.queryVersion()
                    }
                    rojoVersion.set(version)
                    rojoVersionLabel.foreground = UIUtil.getLabelForeground()
                } catch (err: Exception) {
                    rojoVersion.set(err.message ?: "Failed to get rojo version")
                    rojoVersionLabel.foreground = UIUtil.getErrorForeground()
                }
            }
        }
    }

    private suspend fun withLoader(loader: AnimatedIcon, f: suspend () -> Unit) {
        try {
            loader.isVisible = true
            loader.resume()
            f()
        } finally {
            loader.suspend()
            loader.isVisible = false
        }
    }

    fun render(panel: Panel): Row {
        with(panel) {
            return group("LSP") {
                buttonsGroup {
                    row {
                        lspDisabled = radioButton(
                            LuauBundle.message("luau.settings.lsp.disabled"), LspConfigurationType.Disabled
                        ).component
                    }
                    row {
                        lspAuto = radioButton(
                            LuauBundle.message("luau.settings.lsp.managed"), LspConfigurationType.Auto
                        ).component.apply {
                            addItemListener { e ->
                                if (e.stateChange == ItemEvent.SELECTED) {
                                    loadVersions()
                                }
                            }
                        }

                        panel {
                            row {

                                // Initial version show the version we have installed,
                                // or none if user somehow got this state.
                                lspVersionCombobox =
                                    cell(LspVersionComboBox(installedVersions = lspVersionBinding.get()?.let {
                                        if (it is Version.Semantic) {
                                            listOf(it)
                                        } else listOf()
                                    } ?: listOf(),
                                        versionsForDownload = listOf(),
                                        selectedVersion = lspVersionBinding.get(),
                                        download = { version, afterUpdate ->
                                            if (download(version)) {
                                                afterUpdate()
                                            }
                                        })).bind(
                                        { component -> component.getSelectedVersion() },
                                        { component, value -> component.setSelectedVersion(value) },
                                        lspVersionBinding
                                    ).component.apply {
                                        // Disabled until the versions are loaded
                                        isEnabled = false
                                        addItemListener {
                                            if (it.stateChange == ItemEvent.SELECTED) {
                                                updateLspVersionActions(
                                                    lspVersionsForDownload.get(), lspInstalledVersions.get()
                                                )
                                            }
                                        }
                                    }
                                cell(downloadLspButton)
                                cell(lspVersionStateLabelComponent)
                                // I empirically learned that icon will cancel coroutine passed to it if you unload it.
                                lspVersionsLoader =
                                    cell(AsyncProcessIcon("Loading")).visibleIf(lspVersionsForDownload.transform { it is VersionsForDownload.Loading }).component
                                contextHelp("Updates will be suggested if available as notifications when you open a project or you can check for them on this page.").visibleIf(
                                    lspVersionCombobox.selectedValueIs(LspVersionComboBox.Item.LatestVersion)
                                )
                                actionButton(object :
                                    DumbAwareAction("Open LSP Storage Folder", "", AllIcons.Actions.MenuOpen) {
                                    override fun actionPerformed(e: AnActionEvent) {
                                        val lspDir = LuauLspManager.getInstance().basePath().toFile()
                                        if (lspDir.exists()) {
                                            // Could also use ShowFilePathAction
                                            RevealFileAction.openDirectory(lspDir)
                                        }
                                    }
                                    // TODO (AleksandrSl 24/05/2025): Should I show something if it's not available? I'd like to show a copyable path, but i'm yet to fond a good way.
                                    //  Both contextualHelp and rowComment are not copyable.
                                }).align(AlignX.RIGHT).enabled(PlatformCompatibility.isDirectoryOpenSupported())
                            }
                        }.enabledIf(lspAuto.selected)
                    }.rowComment("Binaries are downloaded from <a href='https://github.com/JohnnyMorganz/luau-lsp/releases/latest'>GitHub</a> when you select a version in the download section of combobox.")
                    row {
                        lspManual = radioButton(
                            LuauBundle.message("luau.settings.lsp.manual"), LspConfigurationType.Manual
                        ).component
                    }
                }.bind(settings::lspConfigurationType)
                indent {
                    row("Path to luau lsp:") {
                        cell(lspPathComponent).align(AlignX.FILL).resizableColumn().bindText(settings::lspPath)
                    }
                    row("Version:") {
                        cell(lspVersionLabelComponent).align(AlignX.FILL).resizableColumn()
                    }
                }.visibleIf(lspManual.selected)

                row {
                    sourcemapSupportCheckbox =
                        checkBox(LuauBundle.message("luau.settings.lsp.sourcemap.enabled")).bindSelected(
                            settings::lspSourcemapSupportEnabled
                        ).component
                }.topGap(TopGap.SMALL).enabledIf(!lspDisabled.selected)
                    // TODO (AleksandrSl 12/06/2025): Make visible when I add support for lsp settings file
                    .visible(false)

                collapsibleGroup(LuauBundle.message("luau.settings.lsp.sourcemap.title")) {
                    row("Sourcemap file:") {
                        cell(sourcemapFileComponent).align(AlignX.FILL).resizableColumn()
                            .bindText(settings::lspSourcemapFile)
                    }

                    buttonsGroup {
                        row("Sourcemap generation:") {
                            radioButton("Disabled", LspSourcemapGenerationType.Disabled)
                            sourcemapGenerationRojoRadio =
                                radioButton("Rojo", LspSourcemapGenerationType.Rojo).component.apply {
                                    addItemListener { e ->
                                        if (e.stateChange == ItemEvent.SELECTED) {
                                            checkRojoVersion()
                                        }
                                    }
                                }
                            sourcemapGenerationManulRadio =
                                radioButton("Manual", LspSourcemapGenerationType.Manual).component
                        }
                    }.bind(settings::lspSourcemapGenerationType)

                    panel {
                        row("Rojo version:") {
                            rojoVersionLoader =
                                cell(AsyncProcessIcon("Loading rojo version")).visibleIf(rojoVersion.transform { it.isEmpty() }).component
                            rojoVersionLabel = label("").bindText(rojoVersion).component
                        }
                        row("Rojo project file:") {
                            cell(rojoProjectFileComponent).align(AlignX.FILL).resizableColumn()
                                .bindText(settings::lspRojoProjectFile)
                        }
                    }.visibleIf(sourcemapGenerationRojoRadio.selected)

                    panel {
                        row("Sourcemap Generation Command:") {
                            textField().bindText(settings::lspSourcemapGenerationCommand).align(AlignX.FILL)
                                .resizableColumn()
                        }.rowComment("Command will run from ${SourcemapGeneratorCli.workingDir(project)}")
                        row {
                            checkBox("Use IDEA file watcher").bindSelected(settings::lspSourcemapGenerationUseIdeaWatcher)
                                .comment(
                                    "Enable if the generator doesn't have a builtin watcher, and the specified command will be run on every file creation/deletion"
                                )
                        }
                    }.visibleIf(sourcemapGenerationManulRadio.selected)
                }.topGap(TopGap.NONE)
                    // TODO (AleksandrSl 12/06/2025): Make dependant on sourcemapSupportCheckbox when I add support for lsp settings file
                    .enabledIf(!lspDisabled.selected)
            }
        }
    }

    private fun loadVersions() {
        if (lspAuto.isSelected) {
            coroutineScope.launch {
                withLoader(lspVersionsLoader) {
                    val lspManager = LuauLspManager.getInstance()
                    lspVersionsForDownload.set(
                        try {
                            VersionsForDownload.Loaded(lspManager.getVersionsAvailableForDownload(project))
                        } catch (err: Exception) {
                            VersionsForDownload.Failed(err.message ?: "Failed to load versions")
                        }
                    )
                    lspInstalledVersions.set(
                        try {
                            InstalledVersions.Loaded(lspManager.getInstalledVersions())
                        } catch (err: Exception) {
                            InstalledVersions.Failed(err.message ?: "Failed to get installed versions")
                        }
                    )
                    lspVersionCombobox.setVersions(installedVersions = lspInstalledVersions.get().let {
                        if (it is InstalledVersions.Loaded) {
                            it.versions
                        } else {
                            listOf()
                        }
                    }, versionsForDownload = lspVersionsForDownload.get().let {
                        if (it is VersionsForDownload.Loaded) {
                            it.versions
                        } else {
                            listOf()
                        }
                    })
                    updateLspVersionActions(
                        versionsForDownload = lspVersionsForDownload.get(),
                        installedVersions = lspInstalledVersions.get()
                    )
                }
            }
        }
    }
}

private fun TextFieldWithBrowseButton.onExistingFileChanged(action: (Path) -> Unit) {
    addDocumentListener(object : DocumentAdapter() {
        override fun textChanged(event: javax.swing.event.DocumentEvent) {
            if (text.isEmpty()) {
                return
            }
            val maybePath = text.toNioPathOrNull()
            if (maybePath == null || !maybePath.exists()) {
                return
            }
            action(maybePath)
        }
    })
}

// Yes, this could be a downloadable resource and maybe will be.
sealed class VersionsForDownload {
    data object Loading : VersionsForDownload()
    data class Loaded(val versions: List<Version.Semantic>) : VersionsForDownload()
    data class Failed(val message: String) : VersionsForDownload()
}

sealed class InstalledVersions {
    data object Loading : InstalledVersions()
    data class Loaded(val versions: List<Version.Semantic>) : InstalledVersions()
    data class Failed(val message: String) : InstalledVersions()
}

sealed class LspSettingsUpdate {
    data class Auto(val lspUseLatest: Boolean, val lspVersion: String?) : LspSettingsUpdate()
    data object Manual : LspSettingsUpdate()
    data object Disabled : LspSettingsUpdate()
}
