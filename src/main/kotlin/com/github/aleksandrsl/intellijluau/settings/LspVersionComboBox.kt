package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.settings.LspVersionComboBox.Item
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.popup.ListSeparator
import com.intellij.openapi.util.NlsContexts
import com.intellij.ui.GroupedComboBoxRenderer
import com.intellij.ui.MutableCollectionComboBoxModel

/**
 * A specialized ComboBox for selecting LSP versions
 */
class LspVersionComboBox(
    installedVersions: List<Version.Semantic>,
    versionsForDownload: List<Version.Semantic>,
    selectedVersion: Version?,
    val download: (version: Version.Semantic, afterUpdate: () -> Unit) -> Unit
) :
    ComboBox<Item>() {

    // Dumb way not to make casts everywhere, trying to override the get/set methods with the type I want didn't work
    private val _model
        get() = model as MutableCollectionComboBoxModel<Item>

    init {
        isSwingPopup = false // Use JBPopup instead of default SwingPopup
        model = MutableCollectionComboBoxModel()
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
        }
        setVersions(installedVersions, versionsForDownload)
        setSelectedVersion(selectedVersion)
    }

    fun setVersions(
        installedVersions: List<Version.Semantic>,
        availableVersions: List<Version.Semantic> = listOf()
    ) {
        val selectedVersion = getSelectedVersion()
        _model.removeAll()
        _model.addElement(Item.LatestVersion)
        installedVersions.sortedDescending().forEachIndexed { index, version ->
            _model.addElement(Item.InstalledVersion(version, index))
        }
        availableVersions.filterNot { installedVersions.contains(it) }.sortedDescending()
            .forEachIndexed { index, version ->
                _model.addElement(Item.VersionForDownload(version, index))
            }

        if (selectedVersion != null) {
            val itemToSelect = _model.items
                .filterIsInstance<Item.VersionItem<Version>>()
                .find { it.version == selectedVersion }

            if (itemToSelect != null) {
                selectedItem = itemToSelect
            } else if (model.size > 0) {
                selectedIndex = 0
            }
        }
    }

    // Helper to set the initial version (I don't want to do any actions, neither I want to create an Item manually)
    fun setSelectedVersion(version: Version?) {
        if (version == null) {
            selectedItem = null
        }
        val itemToSelect = _model.items
            .filterIsInstance<Item.VersionItem<Version>>()
            .find { it.version == version }
        selectedItem = itemToSelect
    }

    override fun setSelectedItem(anObject: Any?) {
        if (anObject is Item.VersionForDownload) {
            download(anObject.version) { super.setSelectedItem(anObject) }
        } else {
            super.setSelectedItem(anObject)
        }
    }

    /**
     * Get the currently selected version or null if no version is selected
     */
    fun getSelectedVersion(): Version? {
        return (selectedItem as? Item.VersionItem<*>)?.version
    }

    sealed class Item {
        sealed class VersionItem<T : Version>(open val version: T) : Item() {
            override val text = version.toString()
        }

        class InstalledVersion(version: Version.Semantic, val index: Number) : VersionItem<Version.Semantic>(version)
        class VersionForDownload(version: Version.Semantic, val index: Number) : VersionItem<Version.Semantic>(version)
        object LatestVersion : VersionItem<Version.Latest>(Version.Latest)

        abstract val text: String
    }
}
