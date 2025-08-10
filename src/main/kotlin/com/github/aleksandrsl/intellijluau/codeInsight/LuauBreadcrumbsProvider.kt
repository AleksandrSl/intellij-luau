package com.github.aleksandrsl.intellijluau.codeInsight

import com.github.aleksandrsl.intellijluau.LuauLanguage
import com.github.aleksandrsl.intellijluau.psi.*
import com.intellij.icons.AllIcons
import com.intellij.lang.Language
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.ui.breadcrumbs.BreadcrumbsProvider
import javax.swing.Icon

class LuauBreadcrumbsProvider : BreadcrumbsProvider {
    override fun getLanguages(): Array<Language> = LANGUAGES

    // I was thinking whether I should do this for conditions,
    // and there is even an issue to remove this feature from python because other languages don't do that,
    // So I also won't, at least by default.
    // Though it definitely looks useful for React objects creation, but that's probably a separate "plugin"
    // if I ever come to that
    //
    // Lua plugin includes conditions though.
    override fun acceptElement(element: PsiElement): Boolean {
        return getHelper(element) != null
    }

    private fun getHelper(element: PsiElement, helpers: List<Helper<*>> = STICKY_HELPERS): Helper<in LuauElement>? {
        if (element !is LuauElement) return null

        @Suppress("UNCHECKED_CAST") return helpers.firstOrNull { it.type.isInstance(element) } as Helper<in LuauElement>?
    }

    override fun getElementInfo(element: PsiElement): String =
        getHelper(element)?.elementInfo(element as LuauElement) ?: ""

    override fun getElementIcon(element: PsiElement): Icon? = getHelper(element)?.elementIcon(element as LuauElement)

    private abstract class Helper<T : LuauElement>(val type: Class<T>) {
        fun elementInfo(element: T): String = getTruncatedPresentation(element, 32)

        // TODO (AleksandrSl 22/06/2025): Think if I have something useful for the tooltips
        // Maybe function attributes?
        abstract fun elementIcon(element: T): Icon?

        abstract fun getPresentation(element: T): String

        private fun getTruncatedPresentation(element: T, maxLength: Int) =
            StringUtil.shortenTextWithEllipsis(getPresentation(element), maxLength, 0, true)
    }

    private object MethodHelper : Helper<LuauMethodDefStatement>(LuauMethodDefStatement::class.java) {
        override fun getPresentation(element: LuauMethodDefStatement): String {
            return "${element.methodName.text}"
        }

        override fun elementIcon(element: LuauMethodDefStatement): Icon {
            return AllIcons.Nodes.Method
        }
    }

    private class SimpleHelper<T : LuauNamedElement>(type: Class<T>, val icon: Icon) : Helper<T>(type) {
        override fun getPresentation(element: T): String {
            return element.name ?: "unnamed"
        }

        override fun elementIcon(element: T): Icon {
            return icon
        }
    }

    private object ClosureHelper : Helper<LuauClosureExpr>(LuauClosureExpr::class.java) {
        override fun elementIcon(element: LuauClosureExpr): Icon {
            return AllIcons.Nodes.Lambda
        }

        override fun getPresentation(element: LuauClosureExpr): String {
            return "function${element.funcBody?.parList?.text ?: "()"}"
        }
    }

    override fun isShownByDefault(): Boolean = false

    // They do the same in Python, and I hope they know what they are doing.
    @Suppress("CompanionObjectInExtension")
    companion object {
        private val LANGUAGES = arrayOf<Language>(LuauLanguage.INSTANCE)

        private val STICKY_HELPERS = listOf<Helper<*>>(
            SimpleHelper(LuauFuncDefStatement::class.java, AllIcons.Nodes.Function),
            SimpleHelper(LuauLocalFuncDefStatement::class.java, AllIcons.Nodes.Function),
            SimpleHelper(LuauTypeFunctionDeclarationStatement::class.java, AllIcons.Nodes.Type),
            SimpleHelper(LuauTypeDeclarationStatement::class.java, AllIcons.Nodes.Type),
            ClosureHelper,
            MethodHelper,
        )
    }
}
