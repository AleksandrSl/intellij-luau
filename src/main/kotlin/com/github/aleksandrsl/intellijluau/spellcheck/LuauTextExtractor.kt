package com.github.aleksandrsl.intellijluau.spellcheck

import com.github.aleksandrsl.intellijluau.LuauTokenSets
import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.grazie.text.TextContent
import com.intellij.grazie.text.TextContentBuilder
import com.intellij.grazie.text.TextExtractor
import com.intellij.grazie.utils.getNotSoDistantSimilarSiblings
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiUtilCore


class LuauTextExtractor : TextExtractor() {
    override fun buildTextContent(root: PsiElement, allowedDomains: MutableSet<TextContent.TextDomain>): TextContent? {
        val elementType = PsiUtilCore.getElementType(root) ?: return null

        if (TextContent.TextDomain.LITERALS in allowedDomains && LuauTokenSets.STRINGS.contains(elementType)) {
            return TextContentBuilder.FromPsi.build(root, TextContent.TextDomain.LITERALS)
        }

        if (TextContent.TextDomain.DOCUMENTATION in allowedDomains && (elementType == LuauTypes.DOC_COMMENT || elementType == LuauTypes.DOC_BLOCK_COMMENT)) {
            return TextContentBuilder.FromPsi
                .removingIndents(" \t")
                .removingLineSuffixes(" \t")
                .build(root, TextContent.TextDomain.DOCUMENTATION)
        }

        if (TextContent.TextDomain.COMMENTS in allowedDomains && LuauTokenSets.COMMENTS.contains(elementType)) {
            val siblings =
                getNotSoDistantSimilarSiblings(root) { elementType == PsiUtilCore.getElementType(it) }
            return TextContent.joinWithWhitespace(
                '\n',
                siblings.mapNotNull { TextContent.builder().build(it, TextContent.TextDomain.COMMENTS) })
        }

        return null
    }
}
