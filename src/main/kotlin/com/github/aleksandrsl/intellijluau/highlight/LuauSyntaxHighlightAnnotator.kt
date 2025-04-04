package com.github.aleksandrsl.intellijluau.highlight

import com.github.aleksandrsl.intellijluau.psi.LuauAttribute
import com.github.aleksandrsl.intellijluau.psi.LuauSoftKeyword
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.endOffset
import com.intellij.psi.util.startOffset

class LuauSyntaxHighlightAnnotator : Annotator, DumbAware {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        holder.highlight(element)
    }

    private fun AnnotationHolder.highlight(element: PsiElement) {
        when (element) {
            is LuauSoftKeyword -> softKeyword(element)
            is LuauAttribute -> attribute(element)
        }
    }

    private fun AnnotationHolder.softKeyword(element: PsiElement) {
        newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element)
            .textAttributes(LuauSyntaxHighlighter.KEYWORD)
            .create()
    }

    private fun AnnotationHolder.attribute(element: LuauAttribute) {
        if (element.id != null) {
            newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(element)
                .textAttributes(LuauSyntaxHighlighter.ATTRIBUTE)
                .create()
        } else {
            val list = element.parametrizedAttributeList

            if (list.isEmpty()) {
                newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(element)
                    .textAttributes(LuauSyntaxHighlighter.ATTRIBUTE)
                    .create()
            } else {
                newSilentAnnotation(HighlightSeverity.INFORMATION).range(
                    TextRange(
                        element.startOffset,
                        // I've checked that the list is not empty, so it's fine to do !!
                        list.first()!!.startOffset
                    )
                ).textAttributes(LuauSyntaxHighlighter.ATTRIBUTE)
                    .create()
                newSilentAnnotation(HighlightSeverity.INFORMATION).range(
                    TextRange(
                        list.last()!!.endOffset,
                        element.endOffset
                    )
                ).textAttributes(LuauSyntaxHighlighter.ATTRIBUTE)
                    .create()
                list.forEach { attribute ->
                    newSilentAnnotation(HighlightSeverity.INFORMATION)
                        .range(attribute.id)
                        .textAttributes(LuauSyntaxHighlighter.ATTRIBUTE)
                        .create()
                }
            }

        }
    }
}
