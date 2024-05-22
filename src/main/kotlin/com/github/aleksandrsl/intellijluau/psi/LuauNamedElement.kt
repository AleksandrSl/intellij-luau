package com.github.aleksandrsl.intellijluau.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

interface LuauNamedElement: PsiNameIdentifierOwner {
}

open class LuauNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), LuauNamedElement {
    override fun setName(name: String): PsiElement {
        val keyNode = node.firstChildNode
        if (keyNode != null) {
            val property = createIdentifier(node.psi.project, name)
            val newKeyNode = property.node
            node.replaceChild(keyNode, newKeyNode)
        }
        return node.psi
    }

    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(LuauTypes.ID)?.psi
    }

    override fun getName(): String? {
        return nameIdentifier?.text
    }

    // Should be implemented as said in PsiNameIdentifierOwner docs. How the hell should I know? Should I read all the docs? lol
    override fun getTextOffset(): Int {
        // TODO: I guess !! is fair enough, since without ID this element won't exist.
        // But probably I'll have to subclass this to a specific psi element later
        return nameIdentifier!!.textOffset
    }
}
