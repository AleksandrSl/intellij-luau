package com.github.aleksandrsl.intellijluau.psi

import com.github.aleksandrsl.intellijluau.completion.LuauCompletionScopeProcessor
import com.github.aleksandrsl.intellijluau.references.LuauScopeProcessor
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.ResolveState
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.util.PsiTreeUtil

/**
 * Represents a reference to a variable in Luau code.
 * Handles resolving the reference to its declaration.
 */
abstract class LuauSimpleReferenceImplMixin(node: ASTNode) : PsiReference, LuauSimpleReference,
    ASTWrapperPsiElement(node) {
    override fun getReference(): PsiReference = this
    override fun getElement(): PsiElement = this

    override fun resolve(): PsiElement? {
        return ResolveCache.getInstance(this.project).resolveWithCaching(this, RESOLVER, false, false)
    }

    private fun doResolve(): PsiElement? {
        val processor = LuauScopeProcessor(id.text)
        if (!PsiTreeUtil.treeWalkUp(processor, this, null, ResolveState.initial())) {
            return processor.result
        }
        return null
    }

    override fun getVariants(): Array<out Any?> {
        // I wonder how expensive this call is
        if (ProjectSettingsState.getInstance(this.project).isLspEnabledAndMinimallyConfigured) return emptyArray()

        val processor = LuauCompletionScopeProcessor()
        PsiTreeUtil.treeWalkUp(processor, this, null, ResolveState.initial())
        return processor.results
    }

    override fun getCanonicalText(): String = this.text

    // If I don't want to override this, I need that fckng ElementManipulator.
    override fun getRangeInElement(): TextRange {
        val start = id.textOffset - this.textOffset
        return TextRange(start, start + id.textLength)
    }

    // Most people don't care about ElementManipulator, should I?
    override fun handleElementRename(newElementName: String): PsiElement {
        val newId = createIdentifier(this.project, newElementName)
        this.id.replace(newId)
        return newId
    }

    override fun isReferenceTo(element: PsiElement): Boolean {
        return getElement().manager.areElementsEquivalent(resolve(), element)
    }

    override fun isSoft(): Boolean = false

    // I believe that this method won't be used in my case, since it looks Java specific
    // TODO (AleksandrSl 02/05/2025): Should I warn about its usage? I need to research a way to get telemetry
    override fun bindToElement(element: PsiElement): PsiElement? {
        return this
    }

    companion object {
        private val RESOLVER =
            ResolveCache.AbstractResolver { ref: LuauSimpleReferenceImplMixin, _: Boolean -> ref.doResolve() }
    }
}

