package com.github.aleksandrsl.intellijluau.formatter

import com.github.aleksandrsl.intellijluau.LuauLanguage
import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleConfigurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider
import com.intellij.psi.codeStyle.CustomCodeStyleSettings


class LuauCodeStyleSettingsProvider: CodeStyleSettingsProvider() {
    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings {
        return LuauCodeStyleSettings(settings)
    }

    override fun getConfigurableDisplayName() = LuauLanguage.INSTANCE.displayName

    override fun createConfigurable(
        settings: CodeStyleSettings,
        modelSettings: CodeStyleSettings
    ): CodeStyleConfigurable {
        return object : CodeStyleAbstractConfigurable(settings, modelSettings, this.configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
                return LuauCodeStyleMainPanel(currentSettings, settings)
            }
        }
    }

    class LuauCodeStyleMainPanel(currentSettings: CodeStyleSettings?, settings: CodeStyleSettings) :
        TabbedLanguageCodeStylePanel(LuauLanguage.INSTANCE, currentSettings, settings)
}

