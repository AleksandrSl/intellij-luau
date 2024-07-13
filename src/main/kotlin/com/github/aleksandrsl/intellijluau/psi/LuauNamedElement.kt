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

    // Seems to be called everytime you put your cursor inside the element extending this class.
    // The offset returned here seems to control the highlight of
    // Should be implemented as said in PsiNameIdentifierOwner docs. How the hell should I know? Should I read all the docs? lol
    override fun getTextOffset(): Int {
        // TODO (AleksandrSl 14/07/2024): node.startOffset is a temporary fix while I develop references
        return nameIdentifier?.textOffset ?: node.startOffset
    }
}
