package com.github.aleksandrsl.intellijluau.highlight

import com.github.aleksandrsl.intellijluau.psi.LuauSoftKeyword
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class LuauSyntaxHighlightAnnotator: Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        holder.highlight(element)
    }

    private fun AnnotationHolder.highlight(element: PsiElement) {
        when (element) {
            is LuauSoftKeyword -> softKeyword(element)
        }
    }

    private fun AnnotationHolder.softKeyword(element: PsiElement) {
        newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element)
            .textAttributes(LuauSyntaxHighlighter.KEYWORD)
            .create()
    }
}
