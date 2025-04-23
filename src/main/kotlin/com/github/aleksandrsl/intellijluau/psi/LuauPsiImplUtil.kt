package com.github.aleksandrsl.intellijluau.psi

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil

fun getReference(element: LuauSimpleTypeReference): PsiReference {
    return LuauInternalTypeReference(element)
}

fun getReference(element: LuauSimpleReference): PsiReference {
    return LuauReference(element)
}

fun getDeclaredGenerics(element: LuauTypeDeclarationStatement): Collection<LuauNamedElement> = PsiTreeUtil.findChildrenOfType(
    element.genericTypeListWithDefaults,
    LuauNamedElement::class.java
)

fun processDeclarations(
    element: LuauLocalFuncDefStatement, processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement
): Boolean {
    // If the function doesn't have a name I don't know how the resolve will work at all in half-valid PSI, but let's continue to the parent
    return element.id == null || processor.execute(element, state)
}

fun getDeclaredGenerics(element: LuauFuncBody): Collection<LuauNamedElement> = PsiTreeUtil.findChildrenOfType(
    element.funcTypeParams,
    LuauNamedElement::class.java
)

fun getDeclaredGenerics(element: LuauFunctionType): Collection<LuauNamedElement> = PsiTreeUtil.findChildrenOfType(
    element.funcTypeParams,
    LuauNamedElement::class.java
)

fun getPresentation(element: LuauFuncDefStatement): ItemPresentation {
    return PresentationData().apply {
        // Let's mimic Kotlin text
        presentableText = element.id?.text ?: "no name provided"
        setIcon(AllIcons.Nodes.Function)
    }
}

fun getPresentation(element: LuauLocalFuncDefStatement): ItemPresentation {
    return PresentationData().apply {
        // Let's mimic Kotlin text
        presentableText = element.id?.text ?: "no name provided"
        setIcon(AllIcons.Nodes.Function)
    }
}

fun getPresentation(element: LuauMethodDefStatement): ItemPresentation {
    return PresentationData().apply {
        // Let's mimic Kotlin text
        presentableText = element.methodName.text ?: "no name provided"
        setIcon(AllIcons.Nodes.Method)
    }
}

fun getPresentation(element: LuauBinding): ItemPresentation {
    return PresentationData().apply {
        val type = element.type
        // Let's mimic Kotlin text
        presentableText = "${element.id.text}${if (type != null) ": ${type.text}" else ""}" ?: "no name provided"
        setIcon(AllIcons.Nodes.Variable)
    }
}
