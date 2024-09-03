package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.cli.LuauCliService
import com.github.aleksandrsl.intellijluau.cli.StyLuaCli
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.io.toNioPathOrNull
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.FormBuilder
import kotlinx.coroutines.launch
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JPanel
import kotlin.io.path.exists

private val LOG = logger<ProjectSettingsComponent>()

class ProjectSettingsComponent(private val service: LuauCliService) {
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

    var customDefinitionsPath: String?
        get() = this.customDefinitionsComponent.text
        set(newText) {
            this.customDefinitionsComponent.text = newText ?: ""
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

    private var styLuaVersion: String? = null
    val panel: JPanel
    private val lspPathComponent = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor()))
    }
    private val customDefinitionsComponent = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor()))
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
    private val styluaVersionLabelComponent = JBLabel()
    private val styluaRunOnSaveComponent = JBCheckBox("Run StyLua on save")
    private lateinit var robloxSecurityLevelComponent: JComboBox<String>

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(panel {
                row("Path to luau lsp:") {
                    cell(lspPathComponent).align(AlignX.FILL).resizableColumn()
                }
                row("Roblox Security Level:") {
                    robloxSecurityLevelComponent =
                        comboBox(
                            listOf(
                                RobloxSecurityLevel.None.name,
                                RobloxSecurityLevel.PluginSecurity.name,
                                RobloxSecurityLevel.LocalUserSecurity.name,
                                RobloxSecurityLevel.RobloxScriptSecurity.name,
                                RobloxSecurityLevel.Custom.name,
                            )
                        ).component

                    robloxSecurityLevelComponent.addActionListener {
                        val selected = robloxSecurityLevelComponent.selectedItem?.toString()
                        customDefinitionsComponent.isEnabled = selected == RobloxSecurityLevel.Custom.name
                    }
                }
                row("Custom definitions:") {
                    cell(customDefinitionsComponent.apply {
                        isEnabled = robloxSecurityLevel == RobloxSecurityLevel.Custom.name
                    }).align(AlignX.FILL).resizableColumn()
                }
            })
            .addComponent(panel {
                row("Path to StyLua:") {
                    cell(styLuaPathComponent).align(AlignX.FILL).resizableColumn()
                }.resizableRow()
                row {
                    cell(styluaVersionLabelComponent).align(AlignX.FILL).resizableColumn()
                }
            })
            .addComponent(styluaRunOnSaveComponent)
            .addComponentFillVertically(JPanel(), 0).panel
    }

    private fun setStyluaVersion(newVersion: String) {
        styLuaVersion = newVersion
        // styluaVersionLabel.bindTextIn() Experimental but suits my purpose I guess?
        styluaVersionLabelComponent.text = "Version: $styLuaVersion"
    }

    val preferredFocusedComponent: JComponent = this.lspPathComponent
}
