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

        if (elementType == LuauTypes.EXP_LIST) {
            return Indent.getSmartIndent(Indent.Type.CONTINUATION)
        }

        if (parentType == LuauTypes.IFELSE_EXPR) {
            // I have no idea which indent I need, let's try this one.
            return Indent.getSmartIndent(Indent.Type.CONTINUATION)
        }

        if (needIndent(parentType, elementType)) {
            val psi = node.psi
            if (psi.parent is PsiFile) {
                return Indent.getNoneIndent()
            }
            return Indent.getNormalIndent()
        }

        // About continuation indents. From here - https://www.jetbrains.com/help/idea/code-style-sql-tabs-and-indents.html
        // Specify the indentation for lines that continue from the previous line, making it clear that they are part of the same statement or block of code. Continuation indents are used when a single statement is too long to fit on one line.
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

        if (elementType == LuauTypes.LCURLY || elementType == LuauTypes.RCURLY || elementType == LuauTypes.LBRACK || elementType == LuauTypes.RBRACK || elementType == LuauTypes.LPAREN || elementType == LuauTypes.RPAREN) {
            return false
        }

        if (blockIndentationParents.contains(parentType) && elementType == LuauTypes.BLOCK) {
            return true
        }

        if (parentType == LuauTypes.TABLE_TYPE || parentType == LuauTypes.TABLE_CONSTRUCTOR || parentType == LuauTypes.LIST_ARGS || parentType == LuauTypes.PAR_LIST) {
            return true
        }

        return false
    }

    // Used in getChildAttributes to get indent for the children that will be inserted in the current node.
    fun getChildIndent(node: ASTNode): Indent {
        val elementType = node.elementType
        if (blockIndentationParents.contains(elementType)) {
            return Indent.getNormalIndent()
        }
        if (elementType == LuauTypes.TABLE_TYPE || elementType == LuauTypes.TABLE_CONSTRUCTOR || elementType == LuauTypes.PAR_LIST) {
            return Indent.getNormalIndent()
        }
        if (elementType == LuauTypes.FUNC_ARGS || elementType == LuauTypes.LIST_ARGS) {
            return Indent.getSmartIndent(Indent.Type.CONTINUATION)
        }
        if (elementType == LuauTypes.IFELSE_EXPR) {
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
