package com.github.aleksandrsl.intellijluau.highlight

import com.github.aleksandrsl.intellijluau.LuauStdLibService
import com.github.aleksandrsl.intellijluau.declarations.LuauDeclaration
import com.github.aleksandrsl.intellijluau.psi.*
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

    /*
     * In Rust they do it clever.
     * They highlight all the reference in a separate method.
     * First, trying to color them without looking at what they are referencing, e.g. primitive types
     * If all this fails, they check the reference through resolve and color according to its type.
    * */
    private fun AnnotationHolder.highlight(element: PsiElement): Unit {
        when (element) {
            is LuauSoftKeyword -> softKeyword(element)
            is LuauAttribute -> attribute(element)
            is LuauIndexAccess -> reference(element)
            is LuauFuncCall -> funcCallReference(element)

            /* Types
             * An interesting case is `React.Ref<GuiObject>?`
             * where React itself is not a type, it's a reference to an imported module.
             * So I don't treat it as a type, hence it's not colored as such.
             * It is in VSCode though.
             * Maybe I'll add a settings to change this, or reconsider
             */
            is LuauTypeDeclarationStatement -> typeDeclaration(element)
            is LuauSingletonType -> type(element)
            is LuauSimpleTypeReference -> typeReference(element)
            is LuauFuncTypeParams -> functionTypeParams(element)
            is LuauGenericTypeListWithDefaults -> genericsTypeList(element)
        }
    }

    private fun AnnotationHolder.funcCallReference(element: LuauFuncCall) {
        if (element.indexAccess != null) {
            // It will be processed as the index access later
            return
        }
        element.simpleReferenceList.firstOrNull()?.let { reference ->
            val stdLibService = LuauStdLibService.getInstance(element.project)
            val declaration = stdLibService.resolveReference(reference.text)
            stdLibReference(element, declaration)
        }
    }

    private fun AnnotationHolder.reference(element: LuauIndexAccess) {
        val stdLibService = LuauStdLibService.getInstance(element.project)
        // Let's start simple.
        // Highlight the topmost index access, and ignore the nested ones.
        // TODO (AleksandrSl 08/04/2025): I have a feeling that nested index accesses are fucked and recursively colored
        // Font = props.typography.Font is colored as stdlib because of typography, why? maybe simpleReferenceList works not as I expect it to work 😂
        when (element.parent) {
            is LuauIndexAccess -> {
                // Ignore nested index access to not highlight the parent and then the children separately.
                // At least for now.
                return
            }

            is LuauFuncCall -> {
                val declaration = h(stdLibService, element)
                stdLibReference(element, declaration)
            }
            // Topmost IndexAccess
            is LuauPrimaryGroupExpr -> {
                val declaration = h(stdLibService, element)
                stdLibReference(element, declaration)
            }
        }
    }

    private fun AnnotationHolder.stdLibReference(
        element: PsiElement,
        declaration: LuauDeclaration?
    ) {
        when (declaration) {
            is LuauDeclaration.Class -> {
                if (declaration.isInstance) {
                    return
                }
                newSilentAnnotation(HighlightSeverity.INFORMATION).range(element)
                    .textAttributes(LuauSyntaxHighlighter.STDLIB).create()
            }

            is LuauDeclaration.EnumClass, is LuauDeclaration.Function, is LuauDeclaration.GlobalObject -> {
                // TODO (AleksandrSl 08/04/2025): Correctly check the rest of the elements
                newSilentAnnotation(HighlightSeverity.INFORMATION).range(element)
                    .textAttributes(LuauSyntaxHighlighter.STDLIB).create()
            }

            is LuauDeclaration.TypeAlias -> {
                // TODO (AleksandrSl 08/04/2025): I don't know how I am supposed to use types 🤔
            }

            null -> {}
        }
    }

    private fun h(stdLibService: LuauStdLibService, element: LuauIndexAccess): LuauDeclaration? {
        return if (element.funcCall != null) {
            // Then we are in the a.b().c construction. We will annotate the first a.b part separately.
            null
        } else if (element.indexAccess != null) {
            // a.b.c simpleReferenceList is c here, but we need to start resolving from a.
            h(stdLibService, element.indexAccess!!)
        } else if (element.simpleReferenceList.size == 2) {
            // a.b
            val first = element.simpleReferenceList.firstOrNull() ?: return null

            var reference = first.text
            if (first.text == "Enum") {
                reference = element.simpleReferenceList[1]?.text ?: return null
            }

            stdLibService.resolveReference(reference)
        } else {
            null
        }
    }

    private fun AnnotationHolder.softKeyword(element: PsiElement) {
        newSilentAnnotation(HighlightSeverity.INFORMATION).range(element).textAttributes(LuauSyntaxHighlighter.KEYWORD)
            .create()
    }

    private fun AnnotationHolder.typeDeclaration(element: LuauTypeDeclarationStatement) {
        element.id?.let {
            type(it)
        }
    }

    private fun AnnotationHolder.typeParameter(element: PsiElement) {
        newSilentAnnotation(HighlightSeverity.INFORMATION).range(element)
            .textAttributes(LuauSyntaxHighlighter.TYPE_PARAMETER).create()
    }

    private fun AnnotationHolder.functionTypeParams(element: LuauFuncTypeParams) {
        element.genericTypeList?.run {
            genericTypeDeclarationList.forEach { typeParameter(it.id) }
            // In vscode they don't highlight the ... even though they are part of the type itself
            genericTypePackParameterList.forEach { typeParameter(it.id) }
        }
    }

    private fun AnnotationHolder.genericsTypeList(element: LuauGenericTypeListWithDefaults) {
        element.genericTypeDeclarationList.forEach { typeParameter(it.id) }
        element.genericTypePackParameterList.forEach { typeParameter(it.id) }
        element.genericTypeWithDefaultDeclarationList.forEach { typeParameter(it.id) }
        element.genericTypePackParameterWithDefaultList.forEach { typeParameter(it.id) }
    }

    private fun AnnotationHolder.typeReference(element: LuauSimpleTypeReference) {
        element.reference.resolve()?.takeIf {
            it is LuauGenericTypeDeclaration
        }?.let { typeParameter(element) } ?: type(element)
    }

    private fun AnnotationHolder.type(element: PsiElement) {
        newSilentAnnotation(HighlightSeverity.INFORMATION).range(element).textAttributes(LuauSyntaxHighlighter.TYPE)
            .create()
    }

    private fun AnnotationHolder.attribute(element: LuauAttribute) {
        if (element.id != null) {
            newSilentAnnotation(HighlightSeverity.INFORMATION).range(element)
                .textAttributes(LuauSyntaxHighlighter.ATTRIBUTE).create()
        } else {
            val list = element.parametrizedAttributeList

            if (list.isEmpty()) {
                newSilentAnnotation(HighlightSeverity.INFORMATION).range(element)
                    .textAttributes(LuauSyntaxHighlighter.ATTRIBUTE).create()
            } else {
                newSilentAnnotation(HighlightSeverity.INFORMATION).range(
                    TextRange(
                        element.startOffset,
                        // I've checked that the list is not empty, so it's fine to do !!
                        list.first()!!.startOffset
                    )
                ).textAttributes(LuauSyntaxHighlighter.ATTRIBUTE).create()
                newSilentAnnotation(HighlightSeverity.INFORMATION).range(
                    TextRange(
                        list.last()!!.endOffset, element.endOffset
                    )
                ).textAttributes(LuauSyntaxHighlighter.ATTRIBUTE).create()
                list.forEach { attribute ->
                    newSilentAnnotation(HighlightSeverity.INFORMATION).range(attribute.id)
                        .textAttributes(LuauSyntaxHighlighter.ATTRIBUTE).create()
                }
            }

        }
    }
}
