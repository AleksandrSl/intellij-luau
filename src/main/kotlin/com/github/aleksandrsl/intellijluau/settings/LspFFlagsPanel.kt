package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.tools.LspFlag
import com.github.aleksandrsl.intellijluau.tools.LspFlagType
import com.intellij.ui.BooleanTableCellRenderer
import com.intellij.ui.ColoredTableCellRenderer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.TableSpeedSearch
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.speedSearch.SpeedSearchUtil
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.ListTableModel
import com.intellij.util.ui.UIUtil
import java.awt.BorderLayout
import javax.swing.DefaultCellEditor
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.JTextField
import javax.swing.table.TableCellEditor
import javax.swing.table.TableCellRenderer

private const val NOT_SUPPORTED = "Not supported by the current LSP"

/**
 * A row is either a flag known to the LSP ([flag] != null),
 * or a flag that is recorded in the settings but is unknown to the LSP ([flag] == null).
 * Until the flags are loaded, the recorded flags are shown as is.
 */
private class FlagRow(val name: String, val flag: LspFlag?, var value: String) {
    val isUnsupported get() = flag == null
    val isBoolean get() = flag?.type == LspFlagType.Bool
    val isOverridden get() = flag == null || flag.defaultValue != value
}

/**
 * Shows all the flags known to the LSP and allows to override their values.
 * Only the values that differ from the defaults are recorded ([overrides]).
 * The recorded overrides of the flags that the LSP doesn't know (e.g., removed in the newer version) are kept
 * and shown as unsupported, until the user removes them.
 */
class LspFFlagsPanel {
    private var recorded: Map<String, String> = emptyMap()
    private var available: List<LspFlag>? = null
    private var rows: List<FlagRow> = emptyList()

    private val booleanEditor: TableCellEditor =
        DefaultCellEditor(JBCheckBox().apply { horizontalAlignment = JBCheckBox.LEFT })

    // Integer flags. An invalid value keeps the editor open. DefaultCellEditor also commits the value on Enter,
    // without letting the dialog handle the key.
    private val integerEditor: TableCellEditor = object : DefaultCellEditor(JBTextField()) {
        override fun stopCellEditing(): Boolean {
            val field = component as JTextField
            if (field.text.trim().toIntOrNull() == null) {
                field.foreground = JBUI.CurrentTheme.Focus.errorColor(true)
                return false
            }
            field.foreground = UIUtil.getTextFieldForeground()
            return super.stopCellEditing()
        }

        override fun getCellEditorValue(): Any = (component as JTextField).text.trim()
    }

    private val booleanRenderer = BooleanTableCellRenderer(JBCheckBox.LEFT)

    private val nameRenderer = object : ColoredTableCellRenderer() {
        override fun customizeCellRenderer(
            table: JTable, value: Any?, selected: Boolean, hasFocus: Boolean, row: Int, column: Int
        ) {
            val flagRow = value as? FlagRow ?: return
            SpeedSearchUtil.appendFragmentsForSpeedSearch(
                table, flagRow.name, SimpleTextAttributes.REGULAR_ATTRIBUTES, selected, this
            )
        }
    }

    // The speed search gets the cell values, not the rows. Like in VcsDirectoryConfigurationPanel,
    // the column returns the row itself, so the search is limited to this column and the renderer highlights the matches.
    private inner class NameColumn : ColumnInfo<FlagRow, FlagRow>("Flag") {
        override fun valueOf(item: FlagRow) = item
        override fun getRenderer(item: FlagRow): TableCellRenderer = nameRenderer
    }

    // Booleans are shown as checkboxes, integers as text. The type is defined per row.
    private inner class ValueColumn : ColumnInfo<FlagRow, Any>("Value") {
        override fun valueOf(item: FlagRow): Any = if (item.isBoolean) item.value == "true" else item.value
        override fun isCellEditable(item: FlagRow) = true
        override fun setValue(item: FlagRow, value: Any) {
            item.value = value.toString()
        }

        override fun getRenderer(item: FlagRow): TableCellRenderer? = if (item.isBoolean) booleanRenderer else null
        override fun getEditor(item: FlagRow): TableCellEditor = if (item.isBoolean) booleanEditor else integerEditor
        override fun getWidth(table: JTable) = JBUI.scale(80)
    }

    private inner class DefaultColumn : ColumnInfo<FlagRow, String>("Default") {
        override fun valueOf(item: FlagRow) = item.flag?.defaultValue.orEmpty()
        override fun getWidth(table: JTable) = JBUI.scale(80)
    }

    private inner class StatusColumn : ColumnInfo<FlagRow, String>("") {
        override fun valueOf(item: FlagRow) = if (item.isUnsupported && available != null) NOT_SUPPORTED else ""
    }

    private val tableModel = ListTableModel<FlagRow>(NameColumn(), ValueColumn(), DefaultColumn(), StatusColumn())

    private val table = TableView(tableModel).apply {
        setShowGrid(false)
        intercellSpacing = JBUI.emptySize()
        // Commit the value when the user leaves the table (e.g. presses Apply), otherwise it's lost.
        putClientProperty("terminateEditOnFocusLost", true)
        // Type to jump to a flag, like in the other IDE tables. Only the name column returns a row, so only names are searched.
        TableSpeedSearch.installOn(this) { item: Any? -> (item as? FlagRow)?.name.orEmpty() }
    }

    private val statusLabel = JBLabel().apply { foreground = UIUtil.getContextHelpForeground() }

    val component: JPanel = JPanel(BorderLayout(0, JBUI.scale(4))).apply {
        val decorated = ToolbarDecorator.createDecorator(table, null)
            .disableAddAction()
            .disableUpDownActions()
            .setRemoveAction { removeOrResetSelected() }
            .setRemoveActionName("Remove or Reset to Default")
            .setRemoveActionUpdater { table.selection.any { it.isOverridden } }
            .createPanel()
        // The width is not forced, so the dialog is not stretched.
        decorated.preferredSize = JBDimension(-1, 250)

        add(decorated, BorderLayout.CENTER)
        add(statusLabel, BorderLayout.SOUTH)
    }

    /** The values that differ from the defaults, plus the ones that are unknown to the LSP. */
    var overrides: Map<String, String>
        // Don't stop the cell editing here, the getter is polled by the settings dialog to check for modifications.
        get() = rows.filter { it.isOverridden }.associate { it.name to it.value }
        set(value) {
            recorded = value
            rebuild()
        }

    fun setLoading() {
        statusLabel.text = "Loading flags from the LSP…"
    }

    fun setLoadError(message: String) {
        statusLabel.text = message
    }

    fun setAvailableFlags(flags: List<LspFlag>) {
        // Keep the edits made before the flags were loaded.
        recorded = overrides
        available = flags
        statusLabel.text = "${flags.size} flags are available. Flags are applied when the LSP starts."
        rebuild()
    }

    private fun rebuild() {
        val flags = available
        rows = if (flags == null) {
            recorded.map { (name, value) -> FlagRow(name, null, value) }.sortedBy { it.name }
        } else {
            val known = flags.associateBy { it.name }
            val fromFlags = flags.map { FlagRow(it.name, it, recorded[it.name] ?: it.defaultValue) }
            val unknown = recorded.filterKeys { it !in known }.map { (name, value) -> FlagRow(name, null, value) }
            // Unsupported and overridden flags go first, since these are the ones the user cares about.
            (unknown + fromFlags).sortedWith(
                compareBy<FlagRow> { if (it.isUnsupported) 0 else if (it.isOverridden) 1 else 2 }.thenBy { it.name }
            )
        }
        refreshItems()
    }

    private fun refreshItems() {
        table.stopEditing()
        tableModel.items = rows
    }

    private fun removeOrResetSelected() {
        table.stopEditing()
        val selected = table.selection
        selected.forEach { row ->
            if (row.flag != null) row.value = row.flag.defaultValue
        }
        val unsupported = selected.filter { it.isUnsupported }.toSet()
        if (unsupported.isNotEmpty()) {
            rows = rows.filter { it !in unsupported }
        }
        refreshItems()
    }
}
