package com.github.aleksandrsl.intellijluau.completion

import com.github.aleksandrsl.intellijluau.psi.LuauExpressionStatement
import com.github.aleksandrsl.intellijluau.psi.LuauSimpleReference
import com.github.aleksandrsl.intellijluau.psi.LuauTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.project.DumbAware
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext

class LuauKeywordCompletionContributor : CompletionContributor(), DumbAware {
    init {
        extend(
            CompletionType.BASIC,
            declarationPattern(),
            KeywordCompletionProvider(
                "local",
                "function",
                "while",
                "for",
                "export",
                "type",
                "return",
                "do",
                "repeat",
                "if"
            )
        )
    }
}

class KeywordCompletionProvider(private vararg val keywords: String) : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        for (keyword in keywords) {
            val builder = LookupElementBuilder.create(keyword)
                .bold()
                .withInsertHandler(SpaceAfterInsertHandler)
            result.addElement(builder)
        }
    }
}


inline fun <reified E : PsiElement> psiElement(): PsiElementPattern.Capture<E> {
    return psiElement(E::class.java)
}


fun declarationPattern(): PsiElementPattern.Capture<PsiElement> =
    psiElement(LuauTypes.ID).withParents(LuauSimpleReference::class.java, LuauExpressionStatement::class.java)


private object SpaceAfterInsertHandler : InsertHandler<LookupElement> {
    override fun handleInsert(context: InsertionContext, item: LookupElement) {
        WithTailInsertHandler.SPACE.postHandleInsert(context, item)
    }
}
