package com.github.aleksandrsl.intellijluau.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

abstract class LuauBlockImplMixin(node: ASTNode) : ASTWrapperPsiElement(node), LuauElement {
    override fun processDeclarations(
        processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement
    ): Boolean {
        getDeclarations(lastParent).forEach {
            if (!processor.execute(it, state)) {
                return false
            }
        }
        // TODO (AleksandrSl 22/04/2025): This one just returns true, why everyone keeps calling super?
        return super.processDeclarations(processor, state, lastParent, place)
    }

    // There is a chance that using sequence makes things worse.
    // Because there is still some context switching and extra glue code around coroutines,
    // and, given we don't have a ton of declarations, gathering them all to the list may be faster.
    // This way is prettier, it is a several-line change in case I need a full list
    private fun getDeclarations(lastParent: PsiElement?): Sequence<LuauNamedElement> {
        var processLocals = true
        return sequence {
            children.forEach { child ->
                if (child == lastParent) {
                    processLocals = false
                }

                when (child) {
                    // I can't just add LuauNamedElement because types are also named.
                    // Moreover, we treat local and global variables differently.
                    is LuauLocalDefStatement -> {
                        if (child.bindingList != null && processLocals) {
                            yieldAll(child.bindingList!!.bindingList)
                        }
                    }

                    is LuauLocalFuncDefStatement -> if (processLocals) yield(child as LuauNamedElement)

                    is LuauFuncDefStatement -> yield(child as LuauNamedElement)

                    // TODO (AleksandrSl 22/04/2025): I don't believe I correctly implemented name for this one.
                    // I implemented name, but declarations of methods should be scoped,
                    // and I don't know yet how to process them correctly.
                    is LuauMethodDefStatement -> null

                    else -> null
                }
            }
        }
    }
}
