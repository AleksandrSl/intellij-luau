@file:Suppress("UnstableApiUsage")

package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.LuauIcons
import com.github.aleksandrsl.intellijluau.cli.LspCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.LspServerManager
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import com.intellij.platform.lsp.api.lsWidget.LspServerWidgetItem

private val LOG = logger<LuauLspServerSupportProvider>()

class LuauLspServerSupportProvider : LspServerSupportProvider {
    override fun fileOpened(
        project: Project, file: VirtualFile, serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        // fileType may be slow, but it's used in other lsp implementations, so should be fine.
        // It's ok to check the settings here, ts does that and prisma plugin PrismaLspServerActivationRule
        if (file.fileType == LuauFileType) {
            val lspConfiguration = project.getLspConfiguration()
            if (lspConfiguration is LspConfiguration.Enabled) {
                serverStarter.ensureServerStarted(LuauLspServerDescriptor(project, lspConfiguration))
            }
        }
    }

    override fun createLspServerWidgetItem(lspServer: LspServer, currentFile: VirtualFile?): LspServerWidgetItem = object : LspServerWidgetItem(
            lspServer, currentFile,
            LuauIcons.FILE, ProjectSettingsConfigurable::class.java
        ) {
        override val versionPostfix: @NlsSafe String
            get() = lspServer.project.getLspConfiguration().let { if (it is LspConfiguration.Auto && it.version != null) " ${it.version}" else super.versionPostfix }
    }
}

private class LuauLspServerDescriptor(project: Project, private val lspConfiguration: LspConfiguration.Enabled) :
    ProjectWideLspServerDescriptor(
        project,
        LuauBundle.message("luau.lsp.name")
    ) {
    override fun isSupportedFile(file: VirtualFile) = file.fileType == LuauFileType

    override fun createCommandLine(): GeneralCommandLine {
        LuauLspManager.getInstance().checkLsp(project)
        return LspCli(project, lspConfiguration).createLspCli()
    }
}

fun restartLspServerAsync(project: Project) {
    ApplicationManager.getApplication().invokeLater({
        LspServerManager.getInstance(project).stopAndRestartIfNeeded(LuauLspServerSupportProvider::class.java)
    }, project.disposed)
}
