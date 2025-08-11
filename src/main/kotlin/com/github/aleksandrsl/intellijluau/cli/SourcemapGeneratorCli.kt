package com.github.aleksandrsl.intellijluau.cli

import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessOutput
import com.intellij.openapi.project.Project

object SourcemapGeneratorCli {
    fun generate(project: Project): ProcessOutput {
        val settings = ProjectSettingsState.getInstance(project)
        val parts = settings.lspSourcemapGenerationCommand.split(" ")
        val exe = parts[0]

        // TODO (AleksandrSl 23/04/2025): I remember that I thought: "Why do I need the CapturingProcessHandler here" and then I understood, and forgot...
        //  I hope it's mentioned somewhere in commit history maybe
        return CapturingProcessHandler(GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            // Seems that it's null by default
            withWorkDirectory(workingDir(project))
            withCharset(Charsets.UTF_8)
            withExePath(exe)
            // Exclude the exe name
            addParameters(parts.drop(1))
        }).runProcess(5000)
    }

    fun createProcess(project: Project): OSProcessHandler {
        val settings = ProjectSettingsState.getInstance(project)
        return OSProcessHandler.Silent(
            GeneralCommandLine(
                settings.lspSourcemapGenerationCommand.split(" ")
            ).apply {
                // TODO (AleksandrSl 27/05/2025): Should it be console? What's the difference between it and System?
                withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
                withWorkDirectory(project.basePath)
                withCharset(Charsets.UTF_8)
            })
    }

    fun workingDir(project: Project) = project.basePath
}
