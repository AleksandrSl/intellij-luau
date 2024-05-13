package com.github.aleksandrsl.intellijluau.formatter.blocks

import com.github.aleksandrsl.intellijluau.formatter.LuauIndentProcessor
import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock

open class LuauFormatterBlock(
    node: ASTNode, wrap: Wrap?, alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder
) : AbstractBlock(node, wrap, alignment) {

    private val myIndent: Indent = LuauIndentProcessor(null).getChildIndent(node)

    override fun buildChildren(): List<Block> {
        return myNode.getChildren(null).asSequence().filter {
            it.elementType != TokenType.WHITE_SPACE && it.textLength > 0
        }.map {
            LuauFormatterBlock(
                it,
                Wrap.createWrap(WrapType.NONE, false),
                // This little fucker break aligment of function block aligning it to the direct parent + indent. No idea why, but I don't need it anyway.
                null, //Alignment.createAlignment(),
                spacingBuilder
            )
        }.toList()
    }

    override fun getIndent(): Indent {
        return myIndent
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean = myNode.firstChildNode == null
}
