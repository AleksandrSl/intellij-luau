package com.github.aleksandrsl.intellijluau.util

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.intellij.openapi.application.smartReadAction
import com.intellij.openapi.project.Project
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope

suspend fun Project.hasLuauFiles(): Boolean {
    return smartReadAction(this) { FileTypeIndex.containsFileOfType(LuauFileType, GlobalSearchScope.projectScope(this)) }
}
