package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.cli.LspCli
import com.github.aleksandrsl.intellijluau.cli.LuauCliService
import com.github.aleksandrsl.intellijluau.cli.SourcemapGeneratorCli
import com.github.aleksandrsl.intellijluau.cli.StyLuaCli
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.io.toNioPathOrNull
import com.intellij.ui.CollectionListModel
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBRadioButton
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.layout.selected
import kotlinx.coroutines.launch
import javax.swing.JComponent
import kotlin.io.path.exists

private val LOG = logger<ProjectSettingsComponent>()

class ProjectSettingsComponent(
    private val service: LuauCliService,
    private val settings: ProjectSettingsState.State,
    private val project: Project,
) {
    private var lspVersion: String? = null
    private var styLuaVersion: String? = null
    val panel: DialogPanel


    private val lspPathComponent = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor()))
        textField.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(event: javax.swing.event.DocumentEvent) {
                if (text.isEmpty()) {
                    return
                }
                val maybePath = text.toNioPathOrNull()
                if (maybePath == null || !maybePath.exists()) {
                    return
                }
                // I guess this will launch in project scope, so the coroutine will finish even if I close the settings.
                // Not sure if I should about it or not.
                service.coroutineScope.launch {
                    setLspVersion(LspCli(maybePath).queryVersion())
                }
            }
        })
    }
    private val styLuaPathComponent = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor()))
        textField.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(event: javax.swing.event.DocumentEvent) {
                if (text.isEmpty()) {
                    return
                }
                val maybePath = text.toNioPathOrNull()
                if (maybePath == null || !maybePath.exists()) {
                    return
                }
                // I guess this will launch in project scope, so the coroutine will finish even if I close the settings.
                // Not sure if I should about it or not.
                service.coroutineScope.launch {
                    LOG.debug("stylua path: $maybePath")
                    setStyluaVersion(StyLuaCli(maybePath).queryVersion())
                }
            }
        })
    }

    private val lspVersionLabelComponent = JBLabel()
    private val styluaVersionLabelComponent = JBLabel()
    private val customDefinitionsToolbar = CustomDefinitionsToolbar()

    // Required only to easily enable/disable the whole lsp group. Assigned on first form creation.
    private lateinit var lspEnabledRadioButton: JBRadioButton

    init {
        panel = panel {
            group("LSP") {
                row {
                    comment("You can find the latest lsp binaries on <a href='https://github.com/JohnnyMorganz/luau-lsp/releases/latest'>GitHub</a>.")
                }
                row("Path to luau lsp:") {
                    cell(lspPathComponent).align(AlignX.FILL).resizableColumn().bindText(settings::lspPath)
                }
                row {
                    cell(lspVersionLabelComponent).align(AlignX.FILL).resizableColumn()
                }
                buttonsGroup {
                    row {
                        lspEnabledRadioButton = radioButton("Enabled", true).component
                        radioButton("Disabled", false)
                    }
                }.bind(settings::isLspEnabled)
                rowsRange {
                    // TODO (AleksandrSl 24/04/2025): Is there a nice way to disable the ones below when lsp is disabled?
                    //  Bindings are nice, but they don't have the best documentation
                    row("Roblox Security Level:") {
                        comboBox(
                            listOf(
                                RobloxSecurityLevel.None,
                                RobloxSecurityLevel.PluginSecurity,
                                RobloxSecurityLevel.LocalUserSecurity,
                                RobloxSecurityLevel.RobloxScriptSecurity,
                            )
                        ).bindItem(settings::robloxSecurityLevel.toNullableProperty())
                    }
                    row("Sourcemap Generation Command:") {
                        textField().bindText(settings::sourcemapGenerationCommand).align(AlignX.FILL).resizableColumn()
                    }.rowComment("Command will run from ${SourcemapGeneratorCli.workingDir(project)}")
                    collapsibleGroup("Custom Definitions") {
                        row {
                            cell(customDefinitionsToolbar.panel).resizableColumn().align(AlignX.FILL).bind(
                                {
                                    customDefinitionsToolbar.paths.toList()
                                },
                                { _, value -> customDefinitionsToolbar.paths = value },
                                settings::customDefinitionsPaths.toMutableProperty()
                            )
                        }
                    }
                }.enabledIf(lspEnabledRadioButton.selected)
            }
            group("StyLua") {
                row("Path to StyLua:") {
                    cell(styLuaPathComponent).align(AlignX.FILL).resizableColumn().bindText(settings::styLuaPath)
                }.resizableRow()
                row {
                    cell(styluaVersionLabelComponent).align(AlignX.FILL).resizableColumn()
                }
                buttonsGroup("Run:") {
                    row {
                        radioButton("Disabled", RunStyluaOption.Disabled)
                    }
                    row {
                        radioButton("On save", RunStyluaOption.RunOnSave)
                    }
                    row {
                        radioButton(
                            "On save and disable builtin formatter", RunStyluaOption.RunOnSaveAndDisableBuiltinFormatter
                        )
                    }
                    row {
                        radioButton("Instead of builtin formatter", RunStyluaOption.RunInsteadOfFormatter)
                    }
                }.bind(settings::runStyLua)
            }
        }
    }

    private fun setStyluaVersion(newVersion: String) {
        styLuaVersion = newVersion
        // styluaVersionLabel.bindTextIn() Experimental but suits my purpose I guess?
        styluaVersionLabelComponent.text = "Version: $styLuaVersion"
    }

    private fun setLspVersion(newVersion: String) {
        lspVersion = newVersion
        lspVersionLabelComponent.text = "Version: $lspVersion"
    }

    val preferredFocusedComponent: JComponent = this.lspPathComponent
}

class CustomDefinitionsToolbar {

    private val pathsModel = CollectionListModel<String>()
    private val pathsList = JBList(pathsModel)

    var paths: List<String>
        get(): List<String> {
            return pathsModel.items
        }
        set(filePaths) {
            pathsModel.replaceAll(filePaths)
        }

    private val toolbarDecorator = ToolbarDecorator.createDecorator(pathsList).setAddAction { _ -> addFile() }
        .setRemoveAction { _ -> removeSelectedFile() }

    val panel = toolbarDecorator.createPanel()

    private fun addFile() {
        val fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor()
        fileChooserDescriptor.title = "Select File"
        val chosenFile = FileChooser.chooseFile(fileChooserDescriptor, null, null)
        if (chosenFile != null) {
            val filePath = chosenFile.path
            if (filePath.isNotEmpty()) {
                pathsModel.add(filePath)
            }
        }
    }

    private fun removeSelectedFile() {
        val selectedIdx: Int = pathsList.selectedIndex
        if (selectedIdx != -1) {
            pathsModel.remove(selectedIdx)
        }
    }
}
