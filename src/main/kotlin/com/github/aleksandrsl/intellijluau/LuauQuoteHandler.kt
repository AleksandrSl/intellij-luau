package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.codeInsight.editorActions.QuoteHandler
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.highlighter.HighlighterIterator
import kotlin.math.min

class LuauQuoteHandler : QuoteHandler {

    override fun isClosingQuote(iterator: HighlighterIterator, offset: Int): Boolean {
        if (iterator.tokenType == LuauTypes.TEMPLATE_STRING_EQUOTE) {
            return true
        }
        if (iterator.tokenType == LuauTypes.STRING) {
            val start = iterator.start
            val end = iterator.end
            return end - start >= 1 && offset == end - 1
        }
        return false
    }

    override fun isOpeningQuote(iterator: HighlighterIterator, offset: Int): Boolean {
        if (iterator.tokenType == LuauTypes.TEMPLATE_STRING_SQUOTE) {
            return true
        }
        if (iterator.tokenType == LuauTypes.STRING) {
            return offset == iterator.start
        }
        return false
    }

    override fun hasNonClosedLiteral(editor: Editor, iterator: HighlighterIterator, offset: Int): Boolean {
        // Mix of default implementation with hack for template strings
        val start = iterator.start
        try {
            val doc = editor.document
            val chars = doc.charsSequence
            val lineEnd = doc.getLineEndOffset(doc.getLineNumber(offset))

            while (!iterator.atEnd() && iterator.start < lineEnd) {
                val tokenType = iterator.tokenType

                // I did more sophisticated implementation first, but then thought what if I pretend to be silly.
                // It looks like it works the same 🤷.
                if (tokenType === LuauTypes.TEMPLATE_STRING_SQUOTE) {
                    return true
                }
                // Strings are actually allowed to have line ending symbols,
                // so lexer can do them multiline, by eating line endings.
                // However, string can't span several lines.
                // So let's check only until the line end
                if (tokenType === LuauTypes.STRING) {
                    val end = min(lineEnd, iterator.end)
                    if (iterator.start >= end - 1 ||
                        chars[end - 1] != '\"' && chars[end - 1] != '\'' && chars[end - 1] != '`'
                    ) {
                        return true
                    }
                }
                iterator.advance()
            }
        } finally {
            while (iterator.atEnd() || iterator.start != start) iterator.retreat()
        }

        return false
    }

    override fun isInsideLiteral(iterator: HighlighterIterator): Boolean {
        // You can't be inside quotes,
        // so I intentionally only use STRING here and omit template string quotes
        // that are included inside STRING token set for highlighter
        return iterator.tokenType == LuauTypes.STRING
    }
}
