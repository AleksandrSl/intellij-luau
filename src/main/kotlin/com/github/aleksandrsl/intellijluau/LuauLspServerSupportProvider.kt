@file:Suppress("UnstableApiUsage")

package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import java.io.File

class LuauLspServerSupportProvider : LspServerSupportProvider {
    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        if (file.fileType == LuauFileType) {
            serverStarter.ensureServerStarted(LuauLspServerDescriptor(project))
        }
    }
}

private class LuauLspServerDescriptor(project: Project) : ProjectWideLspServerDescriptor(project, "Luau") {
    override fun isSupportedFile(file: VirtualFile) = file.fileType == LuauFileType

    override fun createCommandLine(): GeneralCommandLine {
        val lsp = File(ProjectSettingsState.instance.lspPath)

        if (!lsp.exists()) {
            throw ExecutionException(LuauBundle.message("luau.language.server.not.found"))
        }
        return GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withCharset(Charsets.UTF_8)
            withExePath(lsp.path)
            addParameter("lsp")
        }
    }
}
