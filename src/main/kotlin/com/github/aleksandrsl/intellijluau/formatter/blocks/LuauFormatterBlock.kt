package com.github.aleksandrsl.intellijluau.formatter.blocks

import com.github.aleksandrsl.intellijluau.formatter.LuauIndentProcessor
import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock

// TODO (AleksandrSl 22/03/2025): Take a look at the https://plugins.jetbrains.com/docs/intellij/code-formatting.html#external-code-formatter for stylua
open class LuauFormatterBlock(
    node: ASTNode, wrap: Wrap?, alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder
) : AbstractBlock(node, wrap, alignment) {

    private val myIndentProcessor = LuauIndentProcessor(null)
    private val myIndent: Indent = myIndentProcessor.getIndent(node)

    // TODO (AleksandrSl 17/03/2025):
    //  In Python:
    //  1. Do not create formatter blocks for deeply nested things. function return type of one string has 4 blocks.
    //  I may need at least one for unions later, but definitely not now. The lesser the better.
    //  I also need blocks for things other than indent. But anyway in python every element is one block not 4.
    //  Lines inside the PSI Block are blocks as well. But they are not indented. Only the block is indented
    //  Function arguments are LEFT, WRAP AS NEEDED, NORMAL indent. Braces have no indent. There is not block for all func arguments though.
    //  PSI element covers parentheses and arguments, and then arguments are single elements.
    //  Commas are also indented
    //  2. For tables there is no PSI element other than curly braces plus the internal, and then each key pair is indented on its own.
    //  Each with normal indent. It Values are also normal indented, so if you move them to a new line the have the extra indentation,
    //  and of course the commas are indented
    //  In JS const a = 3 + 3 has a PSI node of `a = 3 + 3` which is a unit with smart continuation indent, because you can move it to the new line. Do I want this, probably not.
    override fun buildChildren(): List<Block> {
        return myNode.getChildren(null).asSequence().filter {
            it.elementType != TokenType.WHITE_SPACE && it.textLength > 0
        }.map {
            LuauFormatterBlock(
                it,
                Wrap.createWrap(WrapType.NONE, false),
                // This little fucker breaks alignment of function block aligning it to the direct parent + indent.
                // No idea why, but I don't need it anyway.
                null, //Alignment.createAlignment(),
                spacingBuilder
            )
        }.toList()
    }

    override fun getIndent(): Indent = myIndent

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean = myNode.firstChildNode == null

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        // TODO: I have little idea how this works well for property lists, I return none, but the children are still correctly aligned
        return ChildAttributes(myIndentProcessor.getChildIndent(myNode), null)
    }
}
