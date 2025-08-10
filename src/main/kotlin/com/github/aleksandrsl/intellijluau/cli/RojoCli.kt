package com.github.aleksandrsl.intellijluau.cli

import com.github.aleksandrsl.intellijluau.lsp.DEFAULT_ROJO_PROJECT_FILE
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.OSProcessHandler
import com.intellij.openapi.project.Project

// Use only global Rojo for now
class RojoCli() {
    fun generateSourcemap(project: Project, rojoProjectFile: String, sourcemapFile: String): OSProcessHandler {
        val actualRojoProjectFile = rojoProjectFile.ifBlank { DEFAULT_ROJO_PROJECT_FILE }
        val actualSourcemapFile = sourcemapFile.ifBlank { "sourcemap.json" }

        return OSProcessHandler.Silent(
            GeneralCommandLine(
                listOf(
                    "rojo",
                    "sourcemap",
                    "--watch",
                    actualRojoProjectFile,
                    "--output",
                    actualSourcemapFile
                )
            ).apply {
                // TODO (AleksandrSl 27/05/2025): Should it be console? What's the difference between it and System?
                withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
                withWorkDirectory(project.basePath)
                withCharset(Charsets.UTF_8)
            })
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
