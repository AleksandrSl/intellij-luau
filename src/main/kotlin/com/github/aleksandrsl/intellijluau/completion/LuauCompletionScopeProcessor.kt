package com.github.aleksandrsl.intellijluau.completion

import com.github.aleksandrsl.intellijluau.psi.LuauNamedElement
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import javax.swing.Icon

/**
 * A processor used during completion that finds all the declarations
 * in the appropriate scope.
 */
class LuauCompletionScopeProcessor() : PsiScopeProcessor {
    private val _results: MutableSet<LookupElement> = mutableSetOf()

    val results: Array<LookupElement>
        get() = _results.toTypedArray()

    /**
     * Processes a declaration during completion
     * @param element The PSI element to check if it's a declaration
     * @return true since we need all the declarations
     */
    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        // IDEA does name filtration later in completion
        if (element is LuauNamedElement) {
            // TODO (AleksandrSl 04/05/2025): I need to handle shadowing both here and in the resolve processor
            val presentation = element.presentation
            var icon: Icon? = null

            if (presentation != null) {
                icon = presentation.getIcon(false)
            }

            var lookupElement = LookupElementBuilder.create(element)

            if (icon != null) lookupElement = lookupElement.withIcon(icon)
            if (element.ty != null) lookupElement = lookupElement.withTypeText(element.ty!!.getTextPresentation())

            _results.add(lookupElement)
        }
        return true // Continue resolving
    }
}
