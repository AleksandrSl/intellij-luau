package com.github.aleksandrsl.intellijluau.cli

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.openapi.diagnostic.logger
import java.nio.file.Path
import kotlin.io.path.pathString

private val LOG = logger<LspCli>()

/**
 * Interact with external `Lsp` process.
 */
class LspCli(private val lspExecutablePath: Path) {

    fun queryVersion(): String {
        val firstLine = CapturingProcessHandler(GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withCharset(Charsets.UTF_8)
            withExePath(lspExecutablePath.pathString)
            addParameter("--version")
//                 TODO: Do lazy reading?
        }).runProcess().stdoutLines.first()
        LOG.warn(firstLine)
        return firstLine
    }
}
