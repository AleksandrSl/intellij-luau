package com.github.aleksandrsl.intellijluau.spellcheck

import com.github.aleksandrsl.intellijluau.LuauLanguage
import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy
import com.intellij.spellchecker.tokenizer.Tokenizer

class LuauSpellCheckingStrategy : SpellcheckingStrategy(), DumbAware {
    override fun isMyContext(element: PsiElement): Boolean {
        return LuauLanguage.INSTANCE.`is`(element.language)
    }

    override fun getTokenizer(element: PsiElement): Tokenizer<*> {
        val type = element.node?.elementType ?: return super.getTokenizer(element)
        // All the parts in the template expressions worth checking have STRING type as well.
        // There is a text tokenizer in the base implementation, but somehow it doesn't work for my string. Probably they are not PsiPlainText.
        // The rest default implementations for comments and identifiers work well enough
        if (type == LuauTypes.STRING) return TEXT_TOKENIZER
        return super.getTokenizer(element)
    }
}
