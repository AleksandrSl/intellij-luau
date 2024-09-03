@file:Suppress("UnstableApiUsage")

package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.application.PluginPathManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import com.intellij.platform.lsp.api.lsWidget.LspServerWidgetItem
import java.io.File

class LuauLspServerSupportProvider : LspServerSupportProvider {
    override fun fileOpened(
        project: Project, file: VirtualFile, serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        if (file.fileType == LuauFileType) {
            serverStarter.ensureServerStarted(LuauLspServerDescriptor(project))
        }
    }

    override fun createLspServerWidgetItem(lspServer: LspServer, currentFile: VirtualFile?): LspServerWidgetItem =
        LspServerWidgetItem(
            lspServer, currentFile,
            LuauIcons.FILE, ProjectSettingsConfigurable::class.java
        )
}

private class LuauLspServerDescriptor(project: Project) : ProjectWideLspServerDescriptor(project, "Luau") {
    override fun isSupportedFile(file: VirtualFile) = file.fileType == LuauFileType

    override fun createCommandLine(): GeneralCommandLine {
        val lsp = File(ProjectSettingsState.getInstance(project).lspPath)

        if (!lsp.exists()) {
            throw ExecutionException(LuauBundle.message("luau.language.server.not.found"))
        }

        val settings = ProjectSettingsState.getInstance(project)
        val file = PluginPathManager.getPluginResource(
            javaClass, "typeDeclarations/globalTypes.${settings.robloxSecurityLevel.name}.d.luau"
        )
        val declarations = settings.customDefinitionsPaths.map { File(it) }.toMutableList().apply {
            if (file != null) {
                add(file)
            }
        }.filter { it.exists() }.map { it.path }

        return GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withCharset(Charsets.UTF_8)
            withExePath(lsp.path)
            addParameter("lsp")
            declarations.forEach {
                addParameter("--definitions")
                addParameter(it)
            }
        }
    }
}
