package com.github.aleksandrsl.intellijluau.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.parentsOfType

// TODO (AleksandrSl 06/04/2025): the most stupid name because I have a clash
class LuauInternalTypeReference(element: LuauSimpleTypeReference) : PsiReferenceBase<LuauSimpleTypeReference>(element) {

    val id = myElement.id

    // What do I want, to simplify finding generic types declared.
    // Maybe even mark the PSI as GenericDeclarationHolder search for it and get its types.
    override fun resolve(): LuauElement? {
        // (╯°□°)╯︵ ┻━┻ getChildrenOfType is not recursive.
        (myElement.parent as LuauTypeReference).let { typeReference ->
            // Our type is SomeModule.Type. Handle this later
            if (typeReference.simpleReference != null) {
                return null
            }
            val text = id.text
            /*
            I should check the types up to the topmost statement, not in the first parent only. Because there is a
            type A<U> = <T>(a: T, b: U) -> (T, U) case. Is it fine to stop at the statement level? I guess there is a worse case of types used inside the function with generics.
            TIL: Types declared inside the function are scoped to the function.
            ```luau
            function test<T>(b: T)
                type A = T
                local c: A = b
            end
            ```
            So I have to check up to the file... Because there can always be a function wrapper on top, and the function can be inside another function.
            Ok, maybe I was thinking too much; the example above is not that trivial, type A is not strictly a generic, it's a type alias for the generic.
            // TODO (AleksandrSl 07/04/2025): Think once again about the resolve. and implement normal types resolve as well.
            */
            // Is there a kotlin alternative?


            // In rust they avoid sequences
            // > Instead of Kotlin `Sequence`'s, a callback (`RsResolveProcessor`) is used because
            //   it gives **much** nicer stacktraces (we used to have `Sequence` here some time ago).
            // But maybe the stacktraces are better now?
            return typeReference.parentsOfType<LuauGenericDeclaration>()
                .firstNotNullOfOrNull { it.declaredGenerics.find { generic -> generic.name == text } }
        }
    }

    // If I don't want to override this, I need that fckng ElementManipulator.
    override fun getRangeInElement(): TextRange {
        val start = id.textOffset - myElement.textOffset
        return TextRange(start, start + id.textLength)
    }

    // Most people don't care about ElementManipulator, should I?
    override fun handleElementRename(newElementName: String): PsiElement {
        val newId = createIdentifier(myElement.project, newElementName)
        myElement.id.replace(newId)
        return newId
    }
}
