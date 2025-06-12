package com.github.aleksandrsl.intellijluau.util

import com.intellij.ide.actions.RevealFileAction
import java.lang.reflect.Method

// TODO (AleksandrSl 12/06/2025): Check how this is done in other plugins, I have a feeling there is a better way.
/** Utilities to provide back-compatible support for older IDEs.
 *
 */
object PlatformCompatibility {
    private var isDirectoryOpenSupportedMethod: Method? = null
    private var methodChecked = false

    fun isDirectoryOpenSupported(): Boolean {
        if (!methodChecked) {
            isDirectoryOpenSupportedMethod = try {
                RevealFileAction::class.java.getMethod("isDirectoryOpenSupported")
            } catch (e: NoSuchMethodException) {
                // Method doesn't exist in this platform version
                null
            }
            methodChecked = true
        }

        return try {
            isDirectoryOpenSupportedMethod?.invoke(null) as? Boolean ?: true
        } catch (e: Exception) {
            true
        }
    }
}
