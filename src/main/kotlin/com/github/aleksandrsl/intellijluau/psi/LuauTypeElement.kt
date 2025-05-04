package com.github.aleksandrsl.intellijluau.psi

import com.github.aleksandrsl.intellijluau.types.LuauTy

// Dummy interface to avoid extending PSI elements on anything outside PSI.
interface LuauTypeElement: LuauTy, LuauElement
