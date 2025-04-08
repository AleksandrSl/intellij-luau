package com.github.aleksandrsl.intellijluau.psi

// Marks all the elements that may declare their own generic types.
// The holder should not be the generic_type_list_with_defaults or similar, but a top level element,
// Because it's used to check for references within types used in the same element.
interface LuauGenericDeclaration : LuauElement {
    val declaredGenerics: Collection<LuauNamedElement>
}
