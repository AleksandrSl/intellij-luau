package com.github.aleksandrsl.intellijluau.formatter

import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import com.intellij.openapi.diagnostic.logger
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

private val LOG = logger<LuauIndentProcessor>()

class LuauIndentProcessor(private val settings: CommonCodeStyleSettings?) {

    fun getChildIndent(node: ASTNode): Indent {
        val elementType: IElementType = node.elementType
        val prevSibling = node.treePrev
        val prevSiblingType: IElementType? = if (prevSibling == null) null else prevSibling.elementType
        val parent: ASTNode? = node.treeParent
        val parentType: IElementType? = if (parent != null) parent.elementType else null
        val superParent: ASTNode? = if (parent == null) null else parent.treeParent
        val superParentType: IElementType? = if (superParent == null) null else superParent.elementType
        val firstChild: ASTNode? = node.firstChildNode
        val firstChildType: IElementType? = if (firstChild == null) null else firstChild.elementType

        LOG.warn("$elementType, $parentType")
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

        if (parentType == LuauTypes.TABLE_TYPE && elementType == LuauTypes.PROP_LIST
            || parentType == LuauTypes.TABLE_CONSTRUCTOR && elementType == LuauTypes.FIELD_LIST) {
            return true
        }

        return false
    }

    private fun isAtFirstColumn(node: ASTNode): Boolean {
        val element = node.psi ?: return false
        val file = element.containingFile
        val project = element.project
        if (null == file) {
            return false
        }
        val doc = PsiDocumentManager.getInstance(project).getDocument(file) ?: return false
        val line = doc.getLineNumber(node.startOffset)
        val lineStart = doc.getLineStartOffset(line)
        return node.startOffset == lineStart
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
