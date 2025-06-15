@file:Suppress("UnstableApiUsage")

package com.github.aleksandrsl.intellijluau.lsp

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.LuauIcons
import com.github.aleksandrsl.intellijluau.cli.LspCli
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsConfigurable
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
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
        if (file.fileType == LuauFileType && ProjectSettingsState.getInstance(project).isLspEnabledAndMinimallyConfigured) {
            serverStarter.ensureServerStarted(LuauLspServerDescriptor(project))
        }
    }

    override fun createLspServerWidgetItem(lspServer: LspServer, currentFile: VirtualFile?): LspServerWidgetItem =
        object : LspServerWidgetItem(
            lspServer, currentFile, LuauIcons.FILE, ProjectSettingsConfigurable::class.java
        ) {
            // The version should be available in the serverInfo > 1.49.1
            override val versionPostfix: @NlsSafe String
                get() {
                    val version =
                        lspServer.initializeResult?.serverInfo?.version ?: lspServer.project.getAutoLspVersion()
                    return if (version != null) " $version" else super.versionPostfix
                }
        }
}

private class LuauLspServerDescriptor(project: Project) : ProjectWideLspServerDescriptor(
    project, LuauBundle.message("luau.lsp.name")
) {
    override fun isSupportedFile(file: VirtualFile) = file.fileType == LuauFileType

    override fun createCommandLine(): GeneralCommandLine {
        // A hacky way to check whether LSP configuration is correct and up to date and provide the feedback only once per LSP start.
        // It would be ideal to do this before creating the descriptor, but the only entry point is fileOpened that is called for all the files open,
        // whenever only one language server is created in the end.
        // Another solution would be to call this check in the LspServerManager listener
        LuauLspManager.getInstance().checkLsp(project)

        val lspConfiguration = project.getLspConfiguration()
        if (lspConfiguration !is LspConfiguration.Enabled) {
            throw IllegalStateException("Tried to created a Luau LSP with disabled configuration")
        }
        return LspCli(project, lspConfiguration).createLspCli()
    }
}

fun restartLspServerAsync(project: Project) {
    ApplicationManager.getApplication().invokeLater({
        LspServerManager.getInstance(project).stopAndRestartIfNeeded(LuauLspServerSupportProvider::class.java)
    }, project.disposed)
}
