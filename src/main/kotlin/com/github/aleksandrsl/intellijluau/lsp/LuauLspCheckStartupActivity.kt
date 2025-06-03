package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauStdLibService
import com.github.aleksandrsl.intellijluau.util.hasLuauFiles
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

private val LOG = logger<LuauStdLibService>()

class LuauLspCheckStartupActivity : ProjectActivity, DumbAware {
    override suspend fun execute(project: Project) {
        val shouldRun = project.hasLuauFiles()
        if (!shouldRun) {
            LOG.info("Luau LSP check startup activity is skipped because there are no Luau files.")
            return
        }
        // TODO (AleksandrSl 03/06/2025): Should I trigger it when the settings are changed
        LuauLspManager.getInstance().checkLsp(project)
    }
}
