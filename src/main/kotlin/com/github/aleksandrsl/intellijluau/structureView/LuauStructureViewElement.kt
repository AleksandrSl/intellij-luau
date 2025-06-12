package com.github.aleksandrsl.intellijluau.structureView

import com.github.aleksandrsl.intellijluau.psi.*
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.diagnostic.logger
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.map2Array

private val LOG = logger<LuauStructureViewElement>()

class LuauStructureViewElement(private val myElement: NavigatablePsiElement, val context: Context? = null) :
    StructureViewTreeElement {
    override fun getPresentation(): ItemPresentation {
        val presentation = myElement.presentation
        return presentation ?: PresentationData().apply { presentableText = "Missing presentation" }
    }

    override fun getChildren(): Array<out TreeElement> {
        when (myElement) {
            is LuauFile -> {
                val methods = PsiTreeUtil
                    .findChildrenOfType(
                        myElement,
                        LuauMethodDefStatement::class.java,
                    )
                return PsiTreeUtil
                    .findChildrenOfAnyType(
                        myElement,
                        LuauFuncDefStatement::class.java,
                        LuauLocalFuncDefStatement::class.java,
                    )
                    .map2Array { LuauStructureViewElement(it as NavigatablePsiElement) }.plus(
                        PsiTreeUtil
                            .findChildrenOfAnyType(
                                myElement,
                                LuauLocalDefStatement::class.java,
                            )
                            .mapNotNull { it.bindingList?.bindingList }.flatten()
                            .map2Array { LuauStructureViewElement(it as NavigatablePsiElement, Context(methods)) })
            }

            is LuauBinding -> {
                // TODO (AleksandrSl 27/06/2024): 1. Write tests
                //  2. Handle methods added to variables defined somewhere else?
                //  I guess I need to create a map of variable to methods beforehand,
                //  because there is no strict hierarchy between them
                //  3. Handle duplicate declarations and their children correctly.
                //  Should we just keep one, or both?
                //  First exists and can be used until the second one is defined.
                //  4. How can I provide different presentation for a method that's a children of another element?
                return context?.methods?.filter {
                    val match = Regex("(.*)[.:][^.:]+").matchEntire(it.methodName.text)
                    match != null && match.groups[1]?.value == myElement.id.text
                }
                    ?.map2Array { LuauStructureViewElement(it as NavigatablePsiElement) } ?: TreeElement.EMPTY_ARRAY
            }

            else -> return TreeElement.EMPTY_ARRAY
        }
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

class Context(val methods: Collection<LuauMethodDefStatement>)
