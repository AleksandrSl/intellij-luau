package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.psi.LuauTypes.*
import com.intellij.psi.tree.TokenSet


object LuauTokenSets {
    val COMMENTS = TokenSet.create(BLOCK_COMMENT, DOC_COMMENT, SHORT_COMMENT, DOC_BLOCK_COMMENT)
    val KEYWORDS = TokenSet.create(
        AND,
        BREAK,
        DO,
        ELSE,
        ELSEIF,
        END,
        FOR,
        FUNCTION,
        IF,
        IN,
        LOCAL,
        NOT,
        OR,
        REPEAT,
        RETURN,
        THEN,
        UNTIL,
        WHILE,
        // Why is this a keyword?
        // Luau type cast operator
        DOUBLE_COLON,
    )
    // Quote handler expects all symbols that represent strings' quotes to be present.
    // I have zero idea how it decides which one is opening and which one is closing if I'm just passing the set.
    // Probably it just makes a decision based on the presence of any token from the set before?
    // But it also should know what is the closed string, whatever it works ok so far.
    val STRINGS = TokenSet.create(STRING, TEMPLATE_STRING_SQUOTE, TEMPLATE_STRING_EQUOTE)
    val ASSIGN_OPERATORS = TokenSet.create(ASSIGN, DIV_EQ, MULT_EQ, PLUS_EQ, MINUS_EQ, EXP_EQ, CONCAT_EQ, DOUBLE_DIV_EQ, MOD_EQ)
    val ADDITIVE_OPERATORS = TokenSet.create(PLUS, MINUS)
    val MULTIPLICATIVE_OPERATORS = TokenSet.create(DIV, MULT, DOUBLE_DIV, MOD)
    val BINARY_LOGICAL_OPERATORS = TokenSet.create(AND, OR)
    val EQUALITY_OPERATORS = TokenSet.create(EQ, NE)
    val RELATIONAL_OPERATORS = TokenSet.create(GT, GE, LT, LE)
}
