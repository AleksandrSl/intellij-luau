package com.github.aleksandrsl.intellijluau.tools

import com.intellij.execution.configurations.GeneralCommandLine


open class ExternalToolCli(val executablePathString: String) {

    protected open fun createBaseCommandLine(
        parameters: List<String>,
        workingDirectory: String? = null,
        environment: Map<String, String> = emptyMap()
    ): GeneralCommandLine =
        GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            // Seems that it's null by default
            withWorkDirectory(workingDirectory)
            withCharset(Charsets.UTF_8)
            withExePath(executablePathString)
            addParameters(parameters)
        }
}
