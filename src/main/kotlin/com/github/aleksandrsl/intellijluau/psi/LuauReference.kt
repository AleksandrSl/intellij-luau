package com.github.aleksandrsl.intellijluau.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.PsiTreeUtil

class LuauReference(element: LuauSimpleReference) : PsiReferenceBase<LuauSimpleReference>(element) {

    val id = myElement.id

    override fun resolve(): PsiElement? {
        // (╯°□°)╯︵ ┻━┻ getChildrenOfType is not recursive.
        return PsiTreeUtil.findChildrenOfType(myElement.containingFile, LuauNamedElement::class.java)
            .find { it.name == id.text }
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
