package com.github.aleksandrsl.intellijluau.cli

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.ProcessOutput
import com.intellij.openapi.project.Project

// Use only global Rojo for now
class RojoCli() {
    fun generateSourcemap(project: Project): ProcessOutput {
        return CapturingProcessHandler(
            GeneralCommandLine(
                listOf(
                    "rojo",
                    "sourcemap",
                    "--watch",
                    "default.project.json",
                    "--output",
                    "sourcemap.json"
                )
            ).apply {
                withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
                withWorkDirectory(project.basePath)
                withCharset(Charsets.UTF_8)
            }).runProcess()
    }

    companion object {
        // Since I don't need any operations on the result, it's fine to get a string, not a Version.Semantic.
        // Also, it helps in case authors release weird non-semantic ones.
        fun queryVersion(): String {
            val firstLine = CapturingProcessHandler(GeneralCommandLine(listOf("rojo", "--version")).apply {
                withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
                withCharset(Charsets.UTF_8)
                // I don't expect a working directory being required to check the version, so I don't specify it
            }).runProcess().stdoutLines.first()
            return firstLine.substringAfter("Rojo ")
        }
    }
}
