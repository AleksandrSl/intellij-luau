package com.github.aleksandrsl.intellijluau.psi

import com.github.aleksandrsl.intellijluau.LuauLanguage
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil

fun createFile(project: Project, content: String): LuauFile {
    val name = "dummy.luau"
    return PsiFileFactory.getInstance(project).createFileFromText(name, LuauLanguage.INSTANCE, content) as LuauFile
}

fun createIdentifier(project: Project, name: String): PsiElement {
    val content = "local $name = 0"
    val file = createFile(project, content)
    val def = PsiTreeUtil.findChildOfType(file, LuauBinding::class.java)!!
    return def.firstChild
}
