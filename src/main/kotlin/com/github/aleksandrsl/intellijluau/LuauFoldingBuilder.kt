package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.psi.LuauFuncBody
import com.github.aleksandrsl.intellijluau.psi.LuauTableConstructor
import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementVisitor
import com.intellij.refactoring.suggested.endOffset

class LuauFoldingBuilder: FoldingBuilderEx(), DumbAware {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {

        val list = mutableListOf<FoldingDescriptor>()

        root.accept(object : PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement) {
                val node = element.node
                when(node.elementType) {
                    LuauTypes.FUNC_BODY -> {
                        // TODO (AleksandrSl 23/05/2024): I don't include ) in parameters list. I think I'll change the grammar at some point.
                        list.add(FoldingDescriptor(node, TextRange((element as LuauFuncBody).parList?.endOffset?.inc() ?: node.startOffset, node.startOffset + node.textLength)))
                    }
                    LuauTypes.TABLE_CONSTRUCTOR -> {
                        list.add(FoldingDescriptor(node, TextRange(node.startOffset, node.startOffset + node.textLength)))
                    }
                    LuauTypes.BLOCK_COMMENT -> {
                        list.add(FoldingDescriptor(node, TextRange(node.startOffset, node.startOffset + node.textLength)))
                    }
                }
                super.visitElement(element)
            }
        })

        return list.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String {
        return when(node.elementType) {
            LuauTypes.FUNC_BODY -> "..."
            LuauTypes.TABLE_CONSTRUCTOR -> (node.psi as LuauTableConstructor).fieldList?.fieldList?.size?.let { "{ $it fields }" } ?: ""
            LuauTypes.BLOCK_COMMENT -> "--[[ ... ]]"
            else -> "..."
        }
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return false
    }
}
