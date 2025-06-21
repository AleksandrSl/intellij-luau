package com.github.aleksandrsl.intellijluau.cli

import com.github.aleksandrsl.intellijluau.lsp.LspConfiguration
import com.github.aleksandrsl.intellijluau.util.Version
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import kotlin.io.path.exists

private val LOG = logger<LspCli>()

/**
 * Interact with external `Lsp` process.
 */
class LspCli(private val project: Project, private val lspConfiguration: LspConfiguration.Enabled) {

    fun createLspCli(): GeneralCommandLine {
        return GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withWorkDirectory(project.basePath)
            withCharset(Charsets.UTF_8)
            withExePath(lspConfiguration.executablePath.toString())
            addParameter("lsp")
            // Maybe it makes sense to check for existence here as well, but since these files are partially
            // user-configured, it's good that they see the errors if the files are missing
            lspConfiguration.definitions.forEach {
                addParameter("--definitions")
                addParameter(it.toString())
            }
            lspConfiguration.docs.filter { it.exists() }.forEach {
                addParameter("--docs")
                addParameter(it.toString())
            }
        }
    }


    fun queryVersion(): Version {
        val firstLine = CapturingProcessHandler(GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withCharset(Charsets.UTF_8)
            withWorkDirectory(project.basePath)
            withExePath(lspConfiguration.executablePath.toString())
            addParameter("--version")
//                 TODO: Do lazy reading?
        }).runProcess().stdoutLines.first()
        return Version.parse(firstLine)
    }
}
