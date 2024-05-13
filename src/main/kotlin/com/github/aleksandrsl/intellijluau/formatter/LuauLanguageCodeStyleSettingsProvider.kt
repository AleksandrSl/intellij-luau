package com.github.aleksandrsl.intellijluau.formatter

import com.github.aleksandrsl.intellijluau.LuauLanguage
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.IndentOptionsEditor
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizableOptions
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider

/**
 * copied from tangzx and modified.
 */
class LuauLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage() = LuauLanguage.INSTANCE

    override fun getCodeSample(settingsType: SettingsType): String {
        return CodeStyleAbstractPanel.readFromFile(this.javaClass, "preview.luau.template")
    }

    override fun getIndentOptionsEditor(): IndentOptionsEditor {
        return SmartIndentOptionsEditor()
    }

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {
        when (settingsType) {
            SettingsType.SPACING_SETTINGS -> {
                consumer.showCustomOption(LuauCodeStyleSettings::class.java, "SPACE_AFTER_TABLE_FIELD_SEP", "After field sep", CodeStyleSettingsCustomizableOptions.getInstance().SPACES_OTHER)
                consumer.showCustomOption(LuauCodeStyleSettings::class.java, "SPACE_AROUND_BINARY_OPERATOR", "Around binary operator", CodeStyleSettingsCustomizableOptions.getInstance().SPACES_OTHER)
                consumer.showCustomOption(LuauCodeStyleSettings::class.java, "SPACE_INSIDE_INLINE_TABLE", "Inside inline table", CodeStyleSettingsCustomizableOptions.getInstance().SPACES_OTHER)
                consumer.showStandardOptions("SPACE_AROUND_ASSIGNMENT_OPERATORS",
                        "SPACE_BEFORE_COMMA",
                        "SPACE_AFTER_COMMA")
            }
            SettingsType.WRAPPING_AND_BRACES_SETTINGS -> {
                consumer.showStandardOptions(
                        "METHOD_PARAMETERS_WRAP",
                        "ALIGN_MULTILINE_PARAMETERS",

                        "CALL_PARAMETERS_WRAP",
                        "ALIGN_MULTILINE_PARAMETERS_IN_CALLS",

                        // keep when reformatting
                        "KEEP_SIMPLE_BLOCKS_IN_ONE_LINE",

                        //align group declarations
                        "ALIGN_CONSECUTIVE_VARIABLE_DECLARATIONS"
                )

                consumer.showCustomOption(LuauCodeStyleSettings::class.java,
                        "ALIGN_TABLE_FIELD_ASSIGN",
                        "Align table field assign",
                        "Table")
            }
            else -> {
            }
        }
    }
}
