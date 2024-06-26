package com.github.aleksandrsl.intellijluau.structureView

import com.github.aleksandrsl.intellijluau.psi.LuauFile
import com.github.aleksandrsl.intellijluau.psi.LuauFuncDefStatement
import com.github.aleksandrsl.intellijluau.psi.LuauLocalFuncDefStatement
import com.github.aleksandrsl.intellijluau.psi.impl.LuauFuncDefStatementImpl
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.map2Array


class LuauStructureViewElement(private val myElement: NavigatablePsiElement) : StructureViewTreeElement {
    override fun getPresentation(): ItemPresentation {
        val presentation = myElement.presentation
        return presentation ?: PresentationData().apply { presentableText = "Missing presentation" }
    }

    override fun getChildren(): Array<TreeElement> {
        if (myElement is LuauFile) {
            return PsiTreeUtil
                .findChildrenOfAnyType(
                    myElement,
                    LuauFuncDefStatement::class.java,
                    LuauLocalFuncDefStatement::class.java
                )
                .map2Array { LuauStructureViewElement(it as NavigatablePsiElement) }
        }
        return TreeElement.EMPTY_ARRAY
    }

    override fun getValue(): NavigatablePsiElement {
        return myElement
    }

    override fun canNavigate(): Boolean {
        return myElement.canNavigate()
    }

    override fun canNavigateToSource(): Boolean {
        return myElement.canNavigateToSource()
    }

    override fun navigate(requestFocus: Boolean) {
        return myElement.navigate(requestFocus)
    }
}
