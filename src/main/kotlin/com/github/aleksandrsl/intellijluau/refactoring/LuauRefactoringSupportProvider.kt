package com.github.aleksandrsl.intellijluau.refactoring

import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement


class LuauRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(
        elementToRename: PsiElement,
        context: PsiElement?
    ): Boolean {
        // Not the wisest thing in the world, but Lua plugin does that
        return true
    }
}
