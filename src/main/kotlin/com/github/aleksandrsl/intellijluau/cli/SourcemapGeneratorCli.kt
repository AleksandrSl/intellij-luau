package com.github.aleksandrsl.intellijluau.cli

import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.ProcessOutput
import com.intellij.openapi.project.Project

object SourcemapGeneratorCli {
    fun generate(project: Project): ProcessOutput {
        val settings = ProjectSettingsState.Companion.getInstance(project)
        val parts = settings.sourcemapGenerationCommand.split(" ")
        val exe = parts[0]

        // TODO (AleksandrSl 23/04/2025): I remember that I thought: "Why do I need the CapturingProcessHandler here" and then I understood, and forgot...
        //  I hope it's mentioned somewhere in commit history maybe
        return CapturingProcessHandler(GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            // Seems that it's null by default
            withWorkDirectory(workingDir(project))
            withCharset(Charsets.UTF_8)
            withExePath(exe)
            addParameters(parts.drop(1))
        }).runProcess(5000)
    }

    fun workingDir(project: Project) = project.basePath
}
