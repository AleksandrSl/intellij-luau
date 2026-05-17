package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.tools.LuauCliService
import com.github.aleksandrsl.intellijluau.lsp.LuauLspPlatformSupportChecker
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.CollectionListModel
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBRadioButton
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.layout.selected
import javax.swing.JComponent

private val LOG = logger<ProjectSettingsComponent>()

class ProjectSettingsComponent(
    service: LuauCliService,
    private val settings: ProjectSettingsState.State,
    project: Project,
    private val applyAndSaveAsDefault: () -> Unit,
) {
    val panel: DialogPanel
    private val lspSettings = if (LuauLspPlatformSupportChecker.isLspSupported) LuauLspSettingsComponent(
        project,
        settings,
        service.coroutineScope
    ) else null
    private lateinit var isRobloxRadioButton: JBRadioButton

    private val customDefinitionsToolbar = CustomDefinitionsToolbar()

    init {
        panel = panel {
            group("General") {
                buttonsGroup {
                    row("Extension of the new file:") {
                        radioButton(".luau", true)
                        radioButton(".lua", false)
                    }
                }.bind(settings::useLuauExtension)

                buttonsGroup {
                    row("Platform:") {
                        isRobloxRadioButton = radioButton(PlatformType.Roblox.name, PlatformType.Roblox).component
                        radioButton(PlatformType.Standard.name, PlatformType.Standard)
                    }
                }.bind(settings::platformType)
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
            }
            group("Roblox") {
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
            }.visibleIf(isRobloxRadioButton.selected)
            lspSettings?.render(this)
            row {
                button(LuauBundle.message("luau.settings.apply.and.save.as.default")) { applyAndSaveAsDefault() }
            }
        }
    }

    // TODO (AleksandrSl 18/05/2025):
    val preferredFocusedComponent: JComponent = this.panel
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
