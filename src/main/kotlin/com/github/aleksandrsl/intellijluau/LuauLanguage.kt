package com.github.aleksandrsl.intellijluau

import com.intellij.lang.Language

class LuauLanguage: Language("luau") {
    companion object {
        val INSTANCE = LuauLanguage()
    }
}
