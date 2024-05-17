package com.github.aleksandrsl.intellijluau

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler

class LuauQuoteHandler: SimpleTokenSetQuoteHandler(LuauTokenSets.STRINGS) {
}
