package com.github.aleksandrsl.intellijluau.psi

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceService
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry

fun getReferences(element: PsiElement): Array<PsiReference> {
    return ReferenceProvidersRegistry.getReferencesFromProviders(element, PsiReferenceService.Hints.NO_HINTS)
}

fun getPresentation(element: LuauFuncDefStatement): ItemPresentation {
    return PresentationData().apply {
        presentableText = element.funcName.text
        setIcon(AllIcons.Nodes.Function)
    }
}
