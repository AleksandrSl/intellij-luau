package com.github.aleksandrsl.intellijluau.formatter

import com.github.aleksandrsl.intellijluau.LuauLanguage
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.IndentOptionsEditor
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider

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
            }
            else -> {
            }
        }
    }
}