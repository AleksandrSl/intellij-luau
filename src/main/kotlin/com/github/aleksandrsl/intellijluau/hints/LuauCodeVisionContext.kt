@file:Suppress("UnstableApiUsage")

package com.github.aleksandrsl.intellijluau.hints

import com.github.aleksandrsl.intellijluau.psi.*
import com.intellij.codeInsight.hints.VcsCodeVisionLanguageContext
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import java.awt.event.MouseEvent


class LuauCodeVisionContext : VcsCodeVisionLanguageContext {
    override fun isAccepted(element: PsiElement): Boolean {
        // TODO (AleksandrSl 11/08/2025): Add for tests later.
        return (element is LuauFuncBody && element.parent !is LuauClosureExpr) || (element is LuauTypeDeclarationStatement && element.exportSoftKeyword != null)
                || (element is LuauReturnStatement && element.parent is LuauRootBlock)
    }

    override fun handleClick(
        mouseEvent: MouseEvent, editor: Editor, element: PsiElement
    ) {
    }
}
