package com.github.aleksandrsl.intellijluau.psi

import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.*
import com.intellij.util.ProcessingContext

// Looks like I did this after https://plugins.jetbrains.com/docs/intellij/reference-contributor.html#register-the-refactoring-support-provider
// But it mentions that this contributor allows to find references in another languages.
// So I think I can delete this later and just use pure getReference stuff like in rust.
// they do not have a PsiReferenceContributor
class LuauReferenceContributor: PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(psiElement().withElementType(LuauTypes.SIMPLE_REFERENCE), NameReferenceProvider())
    }

    internal inner class NameReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(psiElement: PsiElement, processingContext: ProcessingContext): Array<PsiReference> {
            return arrayOf(LuauReference(psiElement as LuauSimpleReference))
        }
    }
}
