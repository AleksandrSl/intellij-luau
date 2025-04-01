package com.github.aleksandrsl.intellijluau

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

class LuauFoldingBuilder : FoldingBuilderEx(), DumbAware {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {

        val list = mutableListOf<FoldingDescriptor>()

        root.accept(object : PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement) {
                val node = element.node
                when (node.elementType) {
                    LuauTypes.BLOCK -> {
                        val prev = node.treePrev
                        val next = node.treeNext
                        if (prev != null && next != null) {
                            val l = prev.startOffset + prev.textLength
                            val r = next.startOffset

                            val range = TextRange(l, r)
                            if (range.length > 0) {
                                list.add(FoldingDescriptor(element, range))
                            }
                        }
                    }

                    LuauTypes.TABLE_CONSTRUCTOR -> {
                        list.add(
                            FoldingDescriptor(
                                node,
                                TextRange(node.startOffset, node.startOffset + node.textLength)
                            )
                        )
                    }

                    LuauTypes.BLOCK_COMMENT -> {
                        list.add(
                            FoldingDescriptor(
                                node,
                                TextRange(node.startOffset, node.startOffset + node.textLength)
                            )
                        )
                    }
                }
                super.visitElement(element)
            }
        })
        return list.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String {
        return when (node.elementType) {
            LuauTypes.BLOCK -> "..."
            LuauTypes.TABLE_CONSTRUCTOR -> (node.psi as LuauTableConstructor).fieldList.size.let { "{ $it fields }" }
            LuauTypes.BLOCK_COMMENT -> "--[[ ... ]]"
            else -> "..."
        }
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return false
    }
}
