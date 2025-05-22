package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.LuauStdLibService
import com.intellij.openapi.application.readAction
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope

private val LOG = logger<LuauStdLibService>()

class LuauLspCheckStartupActivity : ProjectActivity, DumbAware {
    override suspend fun execute(project: Project) {
        val shouldRun = readAction { hasLuauFiles(project) }
        if (!shouldRun) {
            LOG.info("Luau LSP check startup activity is skipped because there are no Luau files.")
            return
        }
        LuauLspManager.getInstance().checkLsp(project)
    }
}

private fun hasLuauFiles(project: Project): Boolean {
    return FileTypeIndex.containsFileOfType(LuauFileType, GlobalSearchScope.projectScope(project))
}
