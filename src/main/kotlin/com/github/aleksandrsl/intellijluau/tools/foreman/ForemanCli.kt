package com.github.aleksandrsl.intellijluau.tools.foreman

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile

object ForemanCli {
    /**
     * Checks if the `foreman` executable is available on the system's PATH.
     * The `lazy` delegate ensures this expensive check is only run once per IDE session.
     */
    val isForemanExecutableOnPath: Boolean by lazy {
        try {
            val commandLine = GeneralCommandLine("foreman", "--version")
            val output = CapturingProcessHandler(commandLine).runProcess(3000)
            output.exitCode == 0
        } catch (e: Exception) {
            false
        }
    }

    // TODO (AleksandrSl 22/08/2025): Find config relative to the file in question I guess?
    fun checkForForemanConfigIncludesTool(project: Project, tool: String): Boolean {
        if (tool == "stylua") {
            val cached = project.getUserData(STYLUA_KEY)
            if (cached != null) return cached
        }

        var config: VirtualFile? = null
        ProjectFileIndex.getInstance(project).iterateContent {
            if (it.name == FOREMAN_CONFIG) {
                config = it
                return@iterateContent false
            }
            return@iterateContent true
        }
        return (config?.inputStream?.bufferedReader()?.use { reader -> reader.readText() }?.contains(tool)
            ?: false).also {
            if (tool == "stylua") project.putUserData(STYLUA_KEY, it)
        }
    }

    private val STYLUA_KEY = Key.create<Boolean?>("stylua");
}

const val FOREMAN_CONFIG = "foreman.toml"
