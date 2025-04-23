package com.github.aleksandrsl.intellijluau.references

import com.github.aleksandrsl.intellijluau.psi.LuauNamedElement
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

/**
 * A processor used during variable resolution to find declarations
 * in the appropriate scope.
 */
class LuauScopeProcessor(private val referenceName: String) : PsiScopeProcessor {
    /**
     * The candidate to which the reference might be resolved.
     * When found, the resolution stops.
     */
    var result: LuauNamedElement? = null
        private set

    /**
     * Processes a declaration during the reference resolution
     * @param element The PSI element to check if it's a declaration
     * @return false if resolution is complete (found a match), true to continue searching
     */
    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is LuauNamedElement && element.name == referenceName) {
            result = element
            return false // Stop resolution
        }
        return true // Continue resolving
    }
}
