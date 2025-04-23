package com.github.aleksandrsl.intellijluau.psi

import com.github.aleksandrsl.intellijluau.references.LuauScopeProcessor
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveState
import com.intellij.psi.util.PsiTreeUtil

/**
 * Represents a reference to a variable in Luau code.
 * Handles resolving the reference to its declaration.
 */
class LuauReference(element: LuauSimpleReference) : PsiReferenceBase<LuauSimpleReference>(element) {

    val id = myElement.id

    override fun resolve(): PsiElement? {
        val processor = LuauScopeProcessor(id.text)

        if (!PsiTreeUtil.treeWalkUp(processor, myElement, null, ResolveState.initial())) {
            return processor.result
        }

        return null
    }

    // If I don't want to override this, I need that fckng ElementManipulator.
    override fun getRangeInElement(): TextRange {
        val start = id.textOffset - myElement.textOffset
        return TextRange(start, start + id.textLength)
    }

    // Most people don't care about ElementManipulator, should I?
    override fun handleElementRename(newElementName: String): PsiElement {
        val newId = createIdentifier(myElement.project, newElementName)
        myElement.id.replace(newId)
        return newId
    }
}
