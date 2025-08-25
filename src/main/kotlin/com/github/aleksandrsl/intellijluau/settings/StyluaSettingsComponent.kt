package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.tools.LuauCliService
import com.github.aleksandrsl.intellijluau.tools.ToolchainResolver
import com.github.aleksandrsl.intellijluau.util.withLoader
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.observable.properties.AtomicProperty
import com.intellij.openapi.observable.util.transform
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.io.toNioPathOrNull
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.bind
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.layout.not
import com.intellij.ui.layout.selected
import com.intellij.util.ui.AnimatedIcon
import com.intellij.util.ui.AsyncProcessIcon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val versionLoader: AnimatedIcon = AsyncProcessIcon("Getting stylua version")
    private val version = AtomicProperty("")

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
                updateStyluaVersion(maybePath)
            }
        })
    }

    fun updateStyluaVersion(maybePath: Path? = null) {
        service.coroutineScope.launch {
            withLoader(versionLoader) {
                try {
                    val version = withContext(Dispatchers.IO) {
                        if (styluaAuto.isSelected) {
                            ToolchainResolver.resolveStyluaForSettings(project, StyluaConfigurationType.Auto, null)
                                ?.queryVersion(project)
                        } else if (styluaManual.isSelected && maybePath != null && maybePath.exists()) {
                            ToolchainResolver.resolveStyluaForSettings(
                                project,
                                StyluaConfigurationType.Manual,
                                maybePath.pathString
                            )?.queryVersion(project)
                        } else {
                            null
                        }
                    }
                    setStyluaVersion(version)
                } catch (e: Exception) {
                    setStyluaVersion(Result.failure(e))
                }
            }
        }
    }

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
                                updateStyluaVersion(null)
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
                                updateStyluaVersion(styLuaPathComponent.text.toNioPathOrNull())
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
                    label("").bindText(version).component
                    cell(versionLoader).visibleIf(version.transform { it.isEmpty() }).component.apply { suspend() }
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
        version.set(newVersion?.fold({
            it
        }, {
            it.message ?: "Unknown error"
        }) ?: "")
    }

    // TODO (AleksandrSl 18/05/2025):
    val preferredFocusedComponent: JComponent = this.panel
}
