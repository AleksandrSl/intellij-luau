package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.psi.tree.TokenSet


object LuauTokenSets {
    val COMMENTS = TokenSet.create(LuauTypes.BLOCK_COMMENT, LuauTypes.DOC_COMMENT, LuauTypes.SHORT_COMMENT, LuauTypes.DOC_BLOCK_COMMENT)
    val KEYWORDS = TokenSet.create(
        LuauTypes.AND,
        LuauTypes.BREAK,
        LuauTypes.DO,
        LuauTypes.ELSE,
        LuauTypes.ELSEIF,
        LuauTypes.END,
        LuauTypes.FOR,
        LuauTypes.FUNCTION,
        LuauTypes.IF,
        LuauTypes.IN,
        LuauTypes.LOCAL,
        LuauTypes.NOT,
        LuauTypes.OR,
        LuauTypes.REPEAT,
        LuauTypes.RETURN,
        LuauTypes.THEN,
        LuauTypes.UNTIL,
        LuauTypes.WHILE,
        // Luau type cast operator
        LuauTypes.DOUBLE_COLON,
    )
}
