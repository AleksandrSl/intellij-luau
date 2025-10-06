package com.github.aleksandrsl.intellijluau.spellcheck

import com.intellij.spellchecker.BundledDictionaryProvider

class LuauBundledDictionaryProvider : BundledDictionaryProvider {
    override fun getBundledDictionaries(): Array<String> = arrayOf(
        "/dictionaries/luau.dic"
    )
}
