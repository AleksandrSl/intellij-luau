package com.github.aleksandrsl.intellijluau.highlight

import com.github.aleksandrsl.intellijluau.LuauTokenSets
import com.github.aleksandrsl.intellijluau.lexer.LuauLexerAdapter
import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.EMPTY_ARRAY
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class LuauSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return LuauLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            in LuauTokenSets.KEYWORDS -> {
                KEYWORD_KEYS
            }
            in LuauTokenSets.COMMENTS -> {
                COMMENT_KEYS
            }
            in LuauTokenSets.STRINGS -> STRING_KEYS
            LuauTypes.NUMBER -> NUMBER_KEYS
            LuauTypes.BIN_OP,
            LuauTypes.UN_OP -> {
                OPERATOR_KEYS
            }
            LuauTypes.ID -> {
                IDENTIFIER_KEYS
            }
            TokenType.BAD_CHARACTER -> {
                BAD_CHAR_KEYS
            }
            else -> {
                EMPTY_KEYS
            }
        }
    }

    companion object {
        val OPERATOR: TextAttributesKey =
            createTextAttributesKey("LUAU_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val KEYWORD: TextAttributesKey =
            createTextAttributesKey("LUAU_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        val IDENTIFIER: TextAttributesKey =
            createTextAttributesKey("LUAU_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
        val COMMENT: TextAttributesKey =
            createTextAttributesKey("LUAU_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val NUMBER = createTextAttributesKey("LUAU_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val STRING = createTextAttributesKey("LUAU_STRING", DefaultLanguageHighlighterColors.STRING)

        val BAD_CHARACTER: TextAttributesKey =
            createTextAttributesKey("LUAU_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val OPERATOR_KEYS = arrayOf(OPERATOR)
        private val KEYWORD_KEYS = arrayOf(KEYWORD)
        private val IDENTIFIER_KEYS = arrayOf(IDENTIFIER)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val NUMBER_KEYS = arrayOf(NUMBER)
        private val STRING_KEYS = arrayOf(STRING)
        private val EMPTY_KEYS = EMPTY_ARRAY
    }
}
