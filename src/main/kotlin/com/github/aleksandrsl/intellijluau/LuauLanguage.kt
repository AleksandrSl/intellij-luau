package com.github.aleksandrsl.intellijluau

import com.intellij.lang.Language

class LuauLanguage: Language("Luau") {
    companion object {
        val INSTANCE = LuauLanguage()
    }
}
