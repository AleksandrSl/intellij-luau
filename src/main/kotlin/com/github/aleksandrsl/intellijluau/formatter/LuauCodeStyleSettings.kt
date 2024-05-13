package com.github.aleksandrsl.intellijluau.formatter

import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CustomCodeStyleSettings

class LuauCodeStyleSettings(settings: CodeStyleSettings) : CustomCodeStyleSettings("LuauCodeStyleSettings", settings) {
    @JvmField
    var SPACE_AFTER_TABLE_FIELD_SEP: Boolean = true
    @JvmField
    var SPACE_AROUND_BINARY_OPERATOR: Boolean = true
    @JvmField
    var SPACE_INSIDE_INLINE_TABLE: Boolean = true
    @JvmField
    var ALIGN_TABLE_FIELD_ASSIGN: Boolean = false
}
