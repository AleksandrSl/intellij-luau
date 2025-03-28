package com.github.aleksandrsl.intellijluau.psi

import com.github.aleksandrsl.intellijluau.LuauLanguage
import com.intellij.psi.tree.IElementType

class LuauTokenType(debugName: String) : IElementType(debugName, LuauLanguage.INSTANCE)
