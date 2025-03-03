package com.github.aleksandrsl.intellijluau.formatter

import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import com.intellij.openapi.diagnostic.logger
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

private val LOG = logger<LuauIndentProcessor>()

class LuauIndentProcessor(private val settings: CommonCodeStyleSettings?) {

    fun getIndent(node: ASTNode): Indent {
        val elementType: IElementType = node.elementType
        val parent: ASTNode? = node.treeParent
        val parentType: IElementType? = parent?.elementType

        if (parent?.treeParent == null) {
            return Indent.getNoneIndent()
        }

        if (needIndent(parentType, elementType)) {
            val psi = node.psi;
            if (psi.parent is PsiFile) {
                return Indent.getNoneIndent();
            }
            return Indent.getNormalIndent();
        }

        if (parentType == LuauTypes.FUNC_ARGS && elementType == LuauTypes.EXP_LIST) {
            // TODO: No idea why smart indent here, but everyone is doing this
            return Indent.getSmartIndent(Indent.Type.CONTINUATION)
        }

        return Indent.getNoneIndent()
    }

    private fun needIndent(parentType: IElementType?, elementType: IElementType): Boolean {
        if (parentType == null) {
            return false
        }

        if (blockIndentationParents.contains(parentType) && elementType == LuauTypes.BLOCK) {
            return true
        }

        if (parentType == LuauTypes.TYPE_TABLE && elementType == LuauTypes.TYPE_FIELD_LIST
            || parentType == LuauTypes.TABLE_CONSTRUCTOR && elementType == LuauTypes.FIELD_LIST) {
            return true
        }

        return false
    }

    fun getChildIndent(node: ASTNode): Indent {
        val elementType = node.elementType
        if (blockIndentationParents.contains(elementType)) {
            return Indent.getNormalIndent()
        }
        if (elementType == LuauTypes.TYPE_TABLE
            || elementType == LuauTypes.TABLE_CONSTRUCTOR) {
            return Indent.getNormalIndent()
        }
        if (elementType == LuauTypes.FUNC_ARGS) {
            return Indent.getSmartIndent(Indent.Type.CONTINUATION)
        }
        return Indent.getNoneIndent()
    }

    companion object {
        val blockIndentationParents = TokenSet.create(
            LuauTypes.CLASSIC_FOR_STATEMENT,
            LuauTypes.FOREACH_STATEMENT,
            LuauTypes.WHILE_STATEMENT,
            LuauTypes.REPEAT_STATEMENT,
            LuauTypes.DO_STATEMENT,
            LuauTypes.IF_STATEMENT,
            LuauTypes.FUNC_BODY,
        )
    }
}
