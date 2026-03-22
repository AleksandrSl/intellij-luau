package com.github.aleksandrsl.intellijluau.lsp

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class CompanionPluginStartupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        CompanionPluginService.getInstance(project)
    }
}
