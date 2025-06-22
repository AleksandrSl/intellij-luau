package com.github.aleksandrsl.intellijluau.lsp

object LspPlatformCompatibility {
    val isLspSupported: Boolean by lazy {
        try {
            Class.forName("com.intellij.platform.lsp.api.LspServerSupportProvider")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}
