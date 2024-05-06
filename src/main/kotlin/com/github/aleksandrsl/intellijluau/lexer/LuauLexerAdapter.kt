package com.github.aleksandrsl.intellijluau.lexer

import com.intellij.lexer.FlexAdapter

class LuauLexerAdapter: FlexAdapter(LuauLexer(null)) {
}
