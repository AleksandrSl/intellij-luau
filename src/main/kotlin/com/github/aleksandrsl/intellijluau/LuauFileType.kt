package com.github.aleksandrsl.intellijluau

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class LuauFileType private constructor() : LanguageFileType(LuauLanguage.INSTANCE) {
    override fun getName(): String {
        return "Luau File"
    }

    override fun getDescription(): String {
        return "Luau config file"
    }

    override fun getDefaultExtension(): String {
        return "luau"
    }

    override fun getIcon(): Icon {
        return LuauIcons.FILE
    }

    companion object {
        val INSTANCE = LuauFileType()
    }
}
