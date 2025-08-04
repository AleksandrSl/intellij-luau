package com.github.aleksandrsl.intellijluau.psi

import com.github.aleksandrsl.intellijluau.types.LuauTy
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.navigation.NavigationItem
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

// NavigationItem added solely to force getPresentation implementation
interface LuauNamedElement : PsiNameIdentifierOwner, NavigationItem, LuauElement {
    // Temporary until I don't figure out how I want to support the types. For now, it's used only for presentation
    // It's not a PsiElement because I believe that I may need to return stubs or fake types
    val ty: LuauTy?
        get() = null
}

open class LuauNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), LuauNamedElement {
    override fun setName(name: String): PsiElement {
        val keyNode = nameIdentifier
        if (keyNode != null) {
            // We use this for things other than local variable, but the psi element is the same
            val property = createIdentifier(node.psi.project, name)
            // Using just node doesn't work if nameIdentifier is nested.
            // (There is an assert replaceChild that the new parent is this)
            // Could be solved by override for method, or by getting an exact parent.
            // Since getting a parent should be a cheap operation I think that's better choice.
            keyNode.parent.node.replaceChild(keyNode.node, property.node)
        }
        return node.psi
    }

    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(LuauTypes.ID)?.psi
    }

    override fun getName(): String? {
        return nameIdentifier?.text
    }

    // Seems to be called everytime you put your cursor inside the element extending this class.
    // The offset returned here seems to control the highlight of
    // Should be implemented as said in PsiNameIdentifierOwner docs. How the hell should I know? Should I read all the docs? lol
    override fun getTextOffset(): Int {
        // TODO (AleksandrSl 14/07/2024): node.startOffset is a temporary fix while I develop references
        return nameIdentifier?.textOffset ?: node.startOffset
    }
}
