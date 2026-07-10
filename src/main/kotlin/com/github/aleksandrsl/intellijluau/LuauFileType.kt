package com.github.aleksandrsl.intellijluau

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object LuauFileType : LanguageFileType(LuauLanguage.INSTANCE) {
    override fun getName(): String {
        // Must match the name in plugin.xml
        return "Luau File"
    }

    override fun getDescription(): String {
        return LuauBundle.message("luau.fileType.description")
    }

    override fun getDefaultExtension(): String {
        return "luau"
    }

    override fun getIcon(): Icon {
        return LuauIcons.FILE
    }
}
