package com.github.aleksandrsl.intellijluau

import com.intellij.codeInsight.generation.IndentedCommenter
import com.intellij.lang.Commenter

class LuauCommenter : Commenter, IndentedCommenter {
    override fun getLineCommentPrefix() = "--"

    override fun getBlockCommentPrefix() = "--[["

    override fun getBlockCommentSuffix() = "]]"

    override fun getCommentedBlockCommentPrefix() = null

    override fun getCommentedBlockCommentSuffix() = null

    override fun forceIndentedLineComment() = true
}
