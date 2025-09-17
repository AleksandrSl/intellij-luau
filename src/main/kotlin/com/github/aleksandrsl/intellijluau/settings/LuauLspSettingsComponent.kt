package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.lsp.LspConfiguration
import com.github.aleksandrsl.intellijluau.lsp.LuauLspManager
import com.github.aleksandrsl.intellijluau.tools.LspCli
import com.github.aleksandrsl.intellijluau.tools.RojoCli
import com.github.aleksandrsl.intellijluau.tools.SourcemapGeneratorCli
import com.github.aleksandrsl.intellijluau.util.PlatformCompatibility
import com.github.aleksandrsl.intellijluau.util.Version
import com.github.aleksandrsl.intellijluau.util.withLoader
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

private val LOG = logger<LuauLspSettingsComponent>()

@JvmInline
value class InstalledLspVersions(val versions: List<Version.Semantic>)

@JvmInline
value class DownloadableLspVersions(val versions: List<Version.Semantic>)

sealed class Loadable<out T> {
    data object Idle : Loadable<Nothing>()
    data object Loading : Loadable<Nothing>()
    data class Loaded<T>(val value: T) : Loadable<T>()
    data class Failed(val message: String) : Loadable<Nothing>()

    val loadedOrNull: T?
        get() = (this as? Loaded<T>)?.value
}

@JvmName("getInstalledOrEmpty")
fun Loadable<InstalledLspVersions>.getOrEmpty(): InstalledLspVersions {
    return loadedOrNull ?: InstalledLspVersions(emptyList())
}

@JvmName("getDownloadableOrEmpty")
fun Loadable<DownloadableLspVersions>.getOrEmpty(): DownloadableLspVersions {
    return loadedOrNull ?: DownloadableLspVersions(emptyList())
}

typealias VersionsForDownload = Loadable<DownloadableLspVersions>
typealias InstalledVersions = Loadable<InstalledLspVersions>


// Important learning, when parent becomes visible it makes all the children visible as well, even if they were explicitly hidden.
// The only escape is to use a predicate or observable
// TODO (AleksandrSl 27/05/2025): Try to use AtomicActions more, since I've switched to them for versions. Now I use it only for the loader.
class LuauLspSettingsComponent(
    private val project: com.intellij.openapi.project.Project,
    private val settings: ProjectSettingsState.State,
    private val coroutineScope: CoroutineScope,
) {
    private lateinit var rojoVersionLabel: JLabel
    private lateinit var rojoVersionLoader: AnimatedIcon
    private val rojoVersion = AtomicProperty("")
    private val lspVersionsForDownload = AtomicProperty<VersionsForDownload>(Loadable.Idle)
    private val lspInstalledVersions = AtomicProperty<InstalledVersions>(Loadable.Idle)

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
    private val lspVersionLabelComponent = JBLabel(if (settings.lspPath.isEmpty()) LuauBundle.message("luau.settings.lsp.no.binary.specified") else "")

    private val lspVersionBinding = object : MutableProperty<Version?> {
        override fun get(): Version = Version.parse(settings.lspVersion)

        override fun set(value: Version?) {
            settings.lspVersion = value.toString()
        }
    }

    private fun download(version: Version.Semantic, afterSuccessfulDownload: () -> Unit = {}) {
        val lspManager = LuauLspManager.getInstance()
        return try {
            runWithModalProgressBlocking(project, LuauBundle.message("luau.lsp.downloading")) {
                when (val result = lspManager.downloadLsp(version)) {
                    is LuauLspManager.DownloadResult.Failed -> {
                        displayDownloadError(LuauBundle.message("luau.lsp.download.failed", version, result.message ?: ""))
                    }

                    is LuauLspManager.DownloadResult.AlreadyExists, is LuauLspManager.DownloadResult.Ok -> {
                        withContext(Dispatchers.EDT) {
                            val updatedInstalledLspVersions = lspInstalledVersions.updateAndGet {
                                // Consider getting versions anew?
                                Loadable.Loaded(InstalledLspVersions(it.getOrEmpty().versions + version))
                            }
                            updateLspVersionActions(lspVersionsForDownload.get(), updatedInstalledLspVersions)
                            lspVersionCombobox.setVersions(
                                installedVersions = updatedInstalledLspVersions.getOrEmpty(),
                                versionsForDownload = lspVersionsForDownload.get().getOrEmpty()
                            )
                            afterSuccessfulDownload()
                        }
                    }
                }
            }
        } catch (err: Exception) {
            displayDownloadError(LuauBundle.message("luau.lsp.download.failed", version, err.message ?: ""))
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
            // I guess this will launch in the project scope, so the coroutine will finish even if I close the settings.
            // Not sure if I should about it or not.
            coroutineScope.launch(Dispatchers.IO) {
                setManualLspVersion(
                    LspCli(
                        project, LspConfiguration.ForSettings(project, it, true)
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
            }
        }
        downloadLspButton.text = if (isUpdate) LuauBundle.message("luau.lsp.update.to", version) else LuauBundle.message("luau.lsp.download.version", version)
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
        if (versionsForDownload is Loadable.Loading || installedVersions is Loadable.Loading) {
            return
        }
        if (versionsForDownload is Loadable.Failed) {
            showLspMessage(versionsForDownload.message, isError = true)
            return
        }
        if (installedVersions is Loadable.Failed) {
            showLspMessage(installedVersions.message, isError = true)
            return
        }
        if (versionsForDownload.getOrEmpty().versions.isEmpty() || installedVersions !is Loadable.Loaded) {
            return
        }
        val selectedVersion = lspVersionCombobox.getSelectedVersion() ?: return
        when (val result = LuauLspManager.checkLsp(
            selectedVersion,
            installedVersions = installedVersions.value.versions,
            versionsAvailableForDownload = versionsForDownload.getOrEmpty().versions,
        )) {
            LuauLspManager.CheckLspResult.LspIsNotConfigured -> showLspMessage(LuauBundle.message("luau.lsp.please.select.version"))
            is LuauLspManager.CheckLspResult.BinaryMissing -> {
                showLspDownloadButton(result.version, isUpdate = false)
            }

            LuauLspManager.CheckLspResult.ReadyToUse -> {
                if (selectedVersion == Version.Latest) {
                    showLspMessage(LuauBundle.message("luau.lsp.up.to.date"))
                } else {
                    // Do nothing if not using the latest; there is no valuable info I can give
                    hideLspRelatedActions()
                }
            }

            is LuauLspManager.CheckLspResult.UpdateAvailable -> {
                showLspDownloadButton(result.version, isUpdate = true)
            }
        }
    }

    private fun checkRojoVersion() {
        coroutineScope.launch {
            withLoader(rojoVersionLoader) {
                try {
                    val version = withContext(Dispatchers.IO) {
                        RojoCli.queryVersion(project)
                    }
                    if (version == null) {
                        rojoVersion.set(LuauBundle.message("luau.settings.lsp.rojo.no.installation"))
                    } else {
                        rojoVersion.set(version)
                    }
                    rojoVersionLabel.foreground = UIUtil.getLabelForeground()
                } catch (err: Exception) {
                    rojoVersion.set(err.message ?: LuauBundle.message("luau.settings.lsp.rojo.failed.to.get.version"))
                    rojoVersionLabel.foreground = UIUtil.getErrorForeground()
                }
            }
        }
    }

    fun render(panel: Panel): Row {
        with(panel) {
            return group(LuauBundle.message("luau.settings.lsp.group.title")) {
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
                                // or none if the user somehow got this state.
                                lspVersionCombobox = cell(
                                    LspVersionComboBox(
                                        installedVersions = InstalledLspVersions(
//                                            Add the currently selected version as though it's actually installed. If it's not, it will be marked as an error later.
                                            lspVersionBinding.get().let {
                                                if (it is Version.Semantic) {
                                                    listOf(it)
                                                } else listOf()
                                            }),
                                        selectedVersion = lspVersionBinding.get(),
                                        download = ::download
                                    )
                                ).bind(
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
                                    cell(AsyncProcessIcon(LuauBundle.message("luau.loading"))).visibleIf(lspVersionsForDownload.transform { it is Loadable.Loading }).component.apply { suspend() }
                                contextHelp(LuauBundle.message("luau.settings.lsp.update.hint")).visibleIf(
                                    lspVersionCombobox.selectedValueIs(LspVersionComboBox.Item.LatestVersion)
                                )
                                actionButton(object :
                                    DumbAwareAction(LuauBundle.message("luau.settings.lsp.open.storage.folder"), "", AllIcons.Actions.MenuOpen) {
                                    override fun actionPerformed(e: AnActionEvent) {
                                        val lspDir = LuauLspManager.lspStorageDirPath.toFile()
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
                    }.rowComment(LuauBundle.message("luau.settings.lsp.downloads.comment"))
                    row {
                        lspManual = radioButton(
                            LuauBundle.message("luau.settings.lsp.manual"), LspConfigurationType.Manual
                        ).component
                    }
                }.bind(settings::lspConfigurationType)
                indent {
                    row(LuauBundle.message("luau.settings.lsp.path")) {
                        cell(lspPathComponent).align(AlignX.FILL).resizableColumn().bindText(settings::lspPath)
                    }
                    row(LuauBundle.message("luau.settings.lsp.version.label")) {
                        cell(lspVersionLabelComponent).align(AlignX.FILL).resizableColumn()
                    }
                }.visibleIf(lspManual.selected)

                row {
                    sourcemapSupportCheckbox =
                        checkBox(LuauBundle.message("luau.settings.lsp.sourcemap.enabled")).bindSelected(
                            settings::lspSourcemapSupportEnabled
                        ).component
                }.topGap(TopGap.SMALL).enabledIf(!lspDisabled.selected)

                collapsibleGroup(LuauBundle.message("luau.settings.lsp.sourcemap.title")) {
                    row(LuauBundle.message("luau.settings.lsp.sourcemap.file")) {
                        cell(sourcemapFileComponent).align(AlignX.FILL).resizableColumn()
                            .bindText(settings::lspSourcemapFile)
                    }

                    buttonsGroup {
                        row(LuauBundle.message("luau.settings.lsp.sourcemap.generation")) {
                            radioButton(LuauBundle.message("luau.settings.lsp.sourcemap.disabled"), LspSourcemapGenerationType.Disabled)
                            sourcemapGenerationRojoRadio =
                                radioButton(LuauBundle.message("luau.settings.lsp.sourcemap.rojo"), LspSourcemapGenerationType.Rojo).component.apply {
                                    addItemListener { e ->
                                        if (e.stateChange == ItemEvent.SELECTED) {
                                            checkRojoVersion()
                                        }
                                    }
                                }
                            sourcemapGenerationManulRadio =
                                radioButton(LuauBundle.message("luau.settings.lsp.sourcemap.manual"), LspSourcemapGenerationType.Manual).component
                        }
                    }.bind(settings::lspSourcemapGenerationType)

                    panel {
                        row(LuauBundle.message("luau.settings.lsp.rojo.version")) {
                            rojoVersionLoader =
                                cell(AsyncProcessIcon(LuauBundle.message("luau.loading.rojo.version"))).visibleIf(rojoVersion.transform { it.isEmpty() }).component.apply { suspend() }
                            rojoVersionLabel = label("").bindText(rojoVersion).component
                        }
                        row(LuauBundle.message("luau.settings.lsp.rojo.project.file")) {
                            cell(rojoProjectFileComponent).align(AlignX.FILL).resizableColumn()
                                .bindText(settings::lspRojoProjectFile)
                        }
                    }.visibleIf(sourcemapGenerationRojoRadio.selected)

                    panel {
                        row(LuauBundle.message("luau.settings.lsp.sourcemap.command")) {
                            textField().bindText(settings::lspSourcemapGenerationCommand).align(AlignX.FILL)
                                .resizableColumn()
                        }.rowComment(LuauBundle.message("luau.settings.lsp.sourcemap.command.comment", SourcemapGeneratorCli.workingDir(project) ?: ""))
                        row {
                            checkBox(LuauBundle.message("luau.settings.lsp.sourcemap.use.idea.watcher")).bindSelected(settings::lspSourcemapGenerationUseIdeaWatcher)
                                .comment(
                                    LuauBundle.message("luau.settings.lsp.sourcemap.use.idea.watcher.comment")
                                )
                        }
                    }.visibleIf(sourcemapGenerationManulRadio.selected)
                }.topGap(TopGap.NONE).enabledIf(sourcemapSupportCheckbox.selected.and(!lspDisabled.selected))
            }
        }
    }

    private fun loadVersions() {
        if (lspAuto.isSelected) {
            coroutineScope.launch {
                withLoader(lspVersionsLoader) {
                    lspVersionsForDownload.set(Loadable.Loading)
                    val lspManager = LuauLspManager.getInstance()
                    lspVersionsForDownload.set(
                        try {
                            Loadable.Loaded(DownloadableLspVersions(lspManager.getVersionsAvailableForDownload(project)))
                        } catch (err: Exception) {
                            Loadable.Failed(err.message ?: LuauBundle.message("luau.lsp.failed.to.load.versions"))
                        }
                    )
                    lspInstalledVersions.set(Loadable.Loading)
                    lspInstalledVersions.set(
                        try {
                            withContext(Dispatchers.IO) {
                                Loadable.Loaded(InstalledLspVersions(LuauLspManager.getInstalledVersions()))
                            }
                        } catch (err: Exception) {
                            Loadable.Failed(err.message ?: LuauBundle.message("luau.lsp.failed.to.get.installed.versions"))
                        })
                    lspVersionCombobox.setVersions(
                        installedVersions = lspInstalledVersions.get().getOrEmpty(),
                        versionsForDownload = lspVersionsForDownload.get().getOrEmpty()
                    )
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

sealed class LspSettingsUpdate {
    data class Auto(val lspUseLatest: Boolean, val lspVersion: String?) : LspSettingsUpdate()
    data object Manual : LspSettingsUpdate()
    data object Disabled : LspSettingsUpdate()
}
