package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.psi.LuauElementType
import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

// Part of this adapted from the Haxe plugin, so maybe I didn't delve into isPairedBracesAllowedBeforeType implementation
class LuauBraceMatcher : PairedBraceMatcher {
    override fun getPairs(): Array<BracePair> {
        return PAIRS
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        if (contextType is LuauElementType) return isPairedBracesAllowedBeforeTypeInHaxe(contextType)
        return true
    }

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }

    private fun isPairedBracesAllowedBeforeTypeInHaxe(tokenType: IElementType?): Boolean {
        // Adding brackets inside brackets don't create pair, but I don't know if I need this.
        return (LuauTokenSets.COMMENTS.contains(tokenType)
                || TokenSet.WHITE_SPACE.contains(tokenType))
                || tokenType === LuauTypes.RPAREN
                || tokenType === LuauTypes.RBRACK
                || tokenType === LuauTypes.RCURLY
                || tokenType === LuauTypes.LCURLY
    }
}

private val PAIRS = arrayOf(
    BracePair(LuauTypes.LBRACK, LuauTypes.RBRACK, false),
    BracePair(LuauTypes.LCURLY, LuauTypes.RCURLY, false),
    BracePair(LuauTypes.LPAREN, LuauTypes.RPAREN, false)
)
