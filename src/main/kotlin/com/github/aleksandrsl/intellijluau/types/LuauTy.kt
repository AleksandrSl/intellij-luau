package com.github.aleksandrsl.intellijluau.types

// Named after rust and to avoid name clashes.
interface LuauTy {
    // Explicitly named Text to avoid conflicts with getPresentation(): PresentationItem
    fun getTextPresentation(): String
}
