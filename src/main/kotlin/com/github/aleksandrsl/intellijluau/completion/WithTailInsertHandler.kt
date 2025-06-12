package com.github.aleksandrsl.intellijluau.completion

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.psi.PsiDocumentManager

class WithTailInsertHandler(
    private val tailText: String,
    private val spaceAfter: Boolean,
) : InsertHandler<LookupElement> {
    override fun handleInsert(context: InsertionContext, item: LookupElement) {
        item.handleInsert(context)
        postHandleInsert(context, item)
    }

    val asPostInsertHandler: InsertHandler<LookupElement>
        get() = InsertHandler { context, item ->
            postHandleInsert(context, item)
        }

    fun postHandleInsert(context: InsertionContext, item: LookupElement) {
        // Is the completionChar the char entered to invoke the completion?
        val completionChar = context.completionChar
        if (completionChar == tailText.singleOrNull() || (spaceAfter && completionChar == ' ')) {
            context.setAddCompletionChar(false)
        }

        val document = context.document
        PsiDocumentManager.getInstance(context.project).doPostponedOperationsAndUnblockDocument(document)

        val tailOffset = context.tailOffset
        val moveCaret = context.editor.caretModel.offset == tailOffset
        var textToInsert = tailText
        if (spaceAfter) textToInsert += " "
        document.insertString(tailOffset, textToInsert)
        if (moveCaret) {
            context.editor.caretModel.moveToOffset(tailOffset + textToInsert.length)
        }
    }

    companion object {
        val COMMA = WithTailInsertHandler(",", spaceAfter = true)
        val RPAREN = WithTailInsertHandler(")", spaceAfter = false)
        val RBRACKET = WithTailInsertHandler("]", spaceAfter = false)
        val RBRACE = WithTailInsertHandler("}", spaceAfter = false)
        val SPACE = WithTailInsertHandler(" ", spaceAfter = false)
    }
}
