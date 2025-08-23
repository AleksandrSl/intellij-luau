package com.github.aleksandrsl.intellijluau.tools

import com.github.aleksandrsl.intellijluau.lsp.SourcemapGeneratorService
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import kotlinx.coroutines.CoroutineScope

@Service(Service.Level.PROJECT)
class LuauCliService(
    private val project: Project,
    val coroutineScope: CoroutineScope
) {
    companion object {
        @JvmStatic
        fun getInstance(project: Project): LuauCliService = project.service()
    }
}
