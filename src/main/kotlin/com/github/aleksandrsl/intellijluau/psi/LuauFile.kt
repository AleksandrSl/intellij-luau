package com.github.aleksandrsl.intellijluau.psi

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.LuauLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class LuauFile(viewProvider: FileViewProvider) :
    PsiFileBase(viewProvider, LuauLanguage.INSTANCE) {

    override fun getFileType(): FileType {
        return LuauFileType
    }

    override fun toString(): String {
        return "Luau file"
    }
}
