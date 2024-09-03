package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.cli.LspCli
import com.github.aleksandrsl.intellijluau.cli.LuauCliService
import com.github.aleksandrsl.intellijluau.cli.StyLuaCli
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.io.toNioPathOrNull
import com.intellij.ui.CollectionListModel
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import kotlinx.coroutines.launch
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JPanel
import kotlin.io.path.exists

private val LOG = logger<ProjectSettingsComponent>()

class ProjectSettingsComponent(private val service: LuauCliService) {

    var customDefinitionsPaths: List<String>
        get() = this.customDefinitionsToolbar.paths.toList()
        set(newPaths) {
            this.customDefinitionsToolbar.paths = newPaths
        }

    var lspPath: String?
        get() = this.lspPathComponent.text
        set(newText) {
            this.lspPathComponent.text = newText ?: ""
        }

    var robloxSecurityLevel: String?
        get() = this.robloxSecurityLevelComponent.selectedItem as String
        set(newText) {
            this.robloxSecurityLevelComponent.selectedItem = newText ?: defaultRobloxSecurityLevel.name
        }

    var styLuaPath: String?
        get() = this.styLuaPathComponent.text
        set(newText) {
            this.styLuaPathComponent.text = newText ?: ""
        }

    var runStyLuaOnSave: Boolean?
        get() = this.styluaRunOnSaveComponent.isSelected
        set(newValue) {
            this.styluaRunOnSaveComponent.isSelected = newValue ?: false
        }

    private var lspVersion: String? = null
    private var styLuaVersion: String? = null
    val panel: JPanel

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
    private val styluaRunOnSaveComponent = JBCheckBox("Run StyLua on save")
    private lateinit var robloxSecurityLevelComponent: JComboBox<String>
    private val customDefinitionsToolbar = CustomDefinitionsToolbar()

    init {
        panel = panel {
            group("LSP") {
                row("Path to luau lsp:") {
                    cell(lspPathComponent).align(AlignX.FILL).resizableColumn()
                }
                row {
                    cell(lspVersionLabelComponent).align(AlignX.FILL).resizableColumn()
                }
                row("Roblox Security Level:") {
                    robloxSecurityLevelComponent = comboBox(
                        listOf(
                            RobloxSecurityLevel.None.name,
                            RobloxSecurityLevel.PluginSecurity.name,
                            RobloxSecurityLevel.LocalUserSecurity.name,
                            RobloxSecurityLevel.RobloxScriptSecurity.name,
                        )
                    ).component
                }
                collapsibleGroup("Custom Definitions") {
                    row {
                        cell(customDefinitionsToolbar.panel).resizableColumn().align(AlignX.FILL)
                    }
                }
            }
            group("StyLua") {
                row("Path to StyLua:") {
                    cell(styLuaPathComponent).align(AlignX.FILL).resizableColumn()
                }.resizableRow()
                row {
                    cell(styluaVersionLabelComponent).align(AlignX.FILL).resizableColumn()
                }
                row {
                    cell(styluaRunOnSaveComponent)
                }
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
