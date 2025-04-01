package com.github.aleksandrsl.intellijluau.cli

import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.OSProcessUtil
import com.intellij.execution.process.ProcessOutput
import com.intellij.openapi.project.Project
import java.nio.file.Path
import kotlin.io.path.pathString

/**
 * Interact with external `RobloxCli` process.
 */
class RobloxCli(private val robloxCliExecutablePath: Path) {


    fun generateSourcemap(project: Project): ProcessOutput {
        val settings = ProjectSettingsState.getInstance(project)

        return CapturingProcessHandler(GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            // Seems that it's null by default
            withWorkDirectory(project.basePath)
            withCharset(Charsets.UTF_8)
            withExePath(robloxCliExecutablePath.pathString)
            addParameter("sourcemap")
            addParameter(settings.rbxpForSourcemapPath)
            addParameter("--output")
            addParameter("sourcemap.json")
        }).runProcess(5000)
    }

    fun queryVersion(): String {
        val firstLine = CapturingProcessHandler(GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withCharset(Charsets.UTF_8)
            withExePath(robloxCliExecutablePath.pathString)
            addParameter("version")
//                 TODO: Do lazy reading?
        }).runProcess().stdoutLines.first()
        return firstLine
    }
}
