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
        // Why is this a keyword?
        // Luau type cast operator
        LuauTypes.DOUBLE_COLON,
    )
    // Quote handler expects all symbols that represent strings' quotes to be present.
    // I have zero idea how it decides which one is opening and which one is closing if I'm just passing the set.
    // Probably it just makes a decision based on the presence of any token from the set before?
    // But it also should know what is the closed string, whatever it works ok so far.
    val STRINGS = TokenSet.create(LuauTypes.STRING, LuauTypes.TEMPLATE_STRING_SQUOTE, LuauTypes.TEMPLATE_STRING_EQUOTE)
}
