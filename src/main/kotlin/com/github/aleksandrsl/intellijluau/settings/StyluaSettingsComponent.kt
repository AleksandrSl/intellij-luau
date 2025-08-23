package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.tools.LuauCliService
import com.github.aleksandrsl.intellijluau.tools.StyLuaCli
import com.github.aleksandrsl.intellijluau.tools.ToolchainResolver
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.io.toNioPathOrNull
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.components.JBLabel
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.bind
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.layout.not
import com.intellij.ui.layout.selected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.event.ItemEvent
import java.nio.file.Path
import javax.swing.JComponent
import javax.swing.JRadioButton
import javax.swing.event.DocumentEvent
import kotlin.io.path.exists
import kotlin.io.path.pathString

private val LOG = logger<StyluaSettingsComponent>()

class StyluaSettingsComponent(
    private val project: Project,
    private val service: LuauCliService,
    private val settings: ProjectSettingsState.State,
) {
    val panel: DialogPanel
    private lateinit var styluaDisabled: JRadioButton
    private lateinit var styluaManual: JRadioButton
    private lateinit var styluaAuto: JRadioButton

    private val styLuaPathComponent = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor()))
        textField.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(event: DocumentEvent) {
                if (text.isEmpty()) {
                    return
                }
                val maybePath = text.toNioPathOrNull()
                if (maybePath == null || !maybePath.exists()) {
                    return
                }
                // I guess this will launch in project scope, so the coroutine will finish even if I close the settings.
                // Not sure if I should about it or not.
                service.coroutineScope.launch(Dispatchers.IO) {
                    updateStyluaVersion(maybePath)
                }
            }
        })
    }

    suspend fun updateStyluaVersion(maybePath: Path? = null) {
        try {
            val version = if (styluaAuto.isSelected) {
                ToolchainResolver.resolveStylua(project)?.queryVersion(project)
            } else if (styluaManual.isSelected && maybePath != null && maybePath.exists()) {
                StyLuaCli(maybePath.pathString).queryVersion(project)
            } else {
                null
            }
            setStyluaVersion(version)
        } catch (e: Exception) {
            setStyluaVersion(Result.failure(e))
        }
    }

    private val styluaVersionLabelComponent = JBLabel()

    init {
        panel = panel {
            buttonsGroup {
                row {
                    styluaDisabled = radioButton(
                        LuauBundle.message("luau.settings.stylua.disabled"),
                        StyluaConfigurationType.Disabled
                    ).component
                }
                row {
                    styluaAuto = radioButton(
                        LuauBundle.message("luau.settings.stylua.auto"),
                        StyluaConfigurationType.Auto
                    ).component.apply {
                        addItemListener { e ->
                            if (e.stateChange == ItemEvent.SELECTED) {
                                service.coroutineScope.launch(Dispatchers.IO) {
                                    updateStyluaVersion(null)
                                }
                            }
                        }
                    }
                }
                row {
                    styluaManual = radioButton(
                        LuauBundle.message("luau.settings.stylua.manual"), StyluaConfigurationType.Manual
                    ).component.apply {
                        addItemListener { e ->
                            if (e.stateChange == ItemEvent.SELECTED) {
                                service.coroutineScope.launch(Dispatchers.IO) {
                                    updateStyluaVersion(styLuaPathComponent.text.toNioPathOrNull())
                                }
                            }
                        }
                    }
                }
            }.bind(settings::styluaConfigurationType)
            rowsRange {
                row("Path to StyLua:") {
                    cell(styLuaPathComponent).align(AlignX.FILL).resizableColumn().bindText(settings::styLuaPath)
                }
            }.visibleIf(styluaManual.selected)
            rowsRange {
                row("Version:") {
                    cell(styluaVersionLabelComponent).align(AlignX.LEFT)
                }.visibleIf(!styluaDisabled.selected)
                buttonsGroup("Run:") {
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
            }.enabledIf(!styluaDisabled.selected)
        }
    }

    private fun setStyluaVersion(newVersion: Result<String>?) {
        styluaVersionLabelComponent.text = newVersion?.fold({
            it
        }, {
            it.message ?: "Unknown error"
        })
        // styluaVersionLabel.bindTextIn() Experimental but suits my purpose I guess?
    }

    // TODO (AleksandrSl 18/05/2025):
    val preferredFocusedComponent: JComponent = this.panel
}
