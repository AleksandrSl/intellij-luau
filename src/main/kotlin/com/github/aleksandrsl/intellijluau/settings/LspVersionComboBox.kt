package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.settings.LspVersionComboBox.Item
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.popup.ListSeparator
import com.intellij.openapi.util.NlsContexts
import com.intellij.ui.GroupedComboBoxRenderer
import com.intellij.ui.MutableCollectionComboBoxModel
import com.intellij.ui.SimpleColoredComponent
import com.intellij.ui.SimpleTextAttributes

private val LOG = logger<LspVersionComboBox>()

/**
 * A specialized ComboBox for selecting LSP versions
 */
@Suppress("UnstableApiUsage")
class LspVersionComboBox(
    installedVersions: InstalledLspVersions = InstalledLspVersions(listOf()),
    versionsForDownload: DownloadableLspVersions = DownloadableLspVersions(listOf()),
    selectedVersion: Version,
    val download: (version: Version.Semantic, afterSuccessfulDownload: () -> Unit) -> Unit
) : ComboBox<Item>() {

    // Dumb way not to make casts everywhere, trying to override the get/set methods with the type I want didn't work
    private val _model
        get() = model as MutableCollectionComboBoxModel<Item>

    private var missingVersion: Version.Semantic? = null

    init {
        isSwingPopup = false // Use JBPopup instead of default SwingPopup
        model = MutableCollectionComboBoxModel()

        // TODO (AleksandrSl 29/06/2025): When I drop support for 242 version I can use the listCellRenderer
        //  Unfortunately I need the separator and it was only added in 243.
        //          return listCellRenderer {
        //                val isErrorValue =
        //                    missingVersion != null && (value as? Item.InstalledVersion)?.version == missingVersion
        //
        //                value.let {
        //                    if (it is Item.InstalledVersion && it.index == 0) {
        //                        separator {
        //                            text = LuauBundle.message("luau.settings.lsp.version.combobox.installed")
        //                        }
        //                    } else if (it is Item.VersionForDownload && it.index == 0) {
        //                        separator {
        //                            text = LuauBundle.message("luau.settings.lsp.version.combobox.download")
        //                        }
        //                    }
        //                }
        //                text(value.text) {
        //                    if (isErrorValue) {
        //                        foreground = UIUtil.getErrorForeground()
        //                    }
        //                }
        //
        //                // There seems to be a bug in the tooltip that shows them on every row
        ////            if (isErrorValue) {
        ////                toolTipText = LuauBundle.message("luau.settings.lsp.version.combobox.missing")
        ////            }
        //            }
        renderer = object : GroupedComboBoxRenderer<Item>() {
            override fun separatorFor(value: Item): ListSeparator? {
                if (value is Item.InstalledVersion && value.index == 0) {
                    return ListSeparator(LuauBundle.message("luau.settings.lsp.version.combobox.installed"))
                }
                if (value is Item.VersionForDownload && value.index == 0) {
                    return ListSeparator(LuauBundle.message("luau.settings.lsp.version.combobox.download"))
                }
                return null
            }

            override fun getText(item: Item): @NlsContexts.ListItem String {
                return item.text
            }

            override fun customize(
                item: SimpleColoredComponent,
                value: Item,
                index: Int,
                isSelected: Boolean,
                cellHasFocus: Boolean
            ) {
                super.customize(item, value, index, isSelected, cellHasFocus)
                val isErrorValue =
                    missingVersion != null && (value as? Item.InstalledVersion)?.version == missingVersion
                if (isErrorValue) {
                    item.clear()
                    item.append(getText(value), SimpleTextAttributes.ERROR_ATTRIBUTES)
                }
            }
        }
        setVersions(selectedVersion, installedVersions, versionsForDownload)
    }

    private fun setVersions(
        selectedVersion: Version?,
        installedVersions: InstalledLspVersions,
        versionsForDownload: DownloadableLspVersions = DownloadableLspVersions(listOf()),
    ) {
        val installedAndSelected = installedVersions.versions.run {
            if (selectedVersion != null && selectedVersion is Version.Semantic && !installedVersions.versions.contains(
                    selectedVersion
                )
            ) {
                missingVersion = selectedVersion
                // Always keep a selected version in the list of installed.
                // Most of the time it's installed.
                // Sometimes it's not, it's an error state, and we must show a download button near.
                // I'm yet to find the way to show the error state
                plus(selectedVersion)
            } else {
                missingVersion = null
                this
            }
        }

        _model.replaceAll(
            listOf(Item.LatestVersion).plus(
                installedAndSelected.sortedDescending()
                    .mapIndexed { index, version -> Item.InstalledVersion(version, index) }).plus(
                versionsForDownload.versions.filterNot { installedAndSelected.contains(it) }.sortedDescending()
                    .mapIndexed { index, version -> Item.VersionForDownload(version, index) })
        )
        setSelectedVersion(selectedVersion)
    }

    fun setVersions(
        installedVersions: InstalledLspVersions,
        versionsForDownload: DownloadableLspVersions
    ) {
        val selectedVersion = (selectedItem as? Item.VersionItem<*>)?.version
        setVersions(selectedVersion, installedVersions, versionsForDownload)
    }

    // Helper to set the initial version (I don't want to create an Item manually)
    fun setSelectedVersion(version: Version?) {
        val itemToSelect = _model.items.filterIsInstance<Item.VersionItem<Version>>().find { it.version == version }
        selectedItem = itemToSelect ?: _model.items.firstOrNull()
    }

    override fun setSelectedItem(anObject: Any?) {
        if (anObject is Item.VersionForDownload) {
            download(anObject.version) {
                setSelectedVersion(anObject.version)
            }
        } else {
            super.setSelectedItem(anObject)
        }
    }

    /**
     * Get the currently selected version
     */
    fun getSelectedVersion(): Version {
        return (selectedItem as? Item.VersionItem<*>)!!.version
    }

    sealed class Item {
        sealed class VersionItem<T : Version>(val version: T) : Item() {
            override val text = version.toString()
        }

        class InstalledVersion(version: Version.Semantic, val index: Number) : VersionItem<Version.Semantic>(version)
        class VersionForDownload(version: Version.Semantic, val index: Number) : VersionItem<Version.Semantic>(version)
        data object LatestVersion : VersionItem<Version.Latest>(Version.Latest)

        abstract val text: String
    }
}
