package com.github.aleksandrsl.intellijluau.tools

import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.settings.StyluaConfigurationType
import com.github.aleksandrsl.intellijluau.tools.foreman.ForemanCli
import com.intellij.openapi.project.Project


object ToolchainResolver {
    fun resolveStylua(project: Project): StyLuaCli? {
        val settings = ProjectSettingsState.getInstance(project)
        return resolveStyluaForSettings(project, settings.styluaConfigurationType, settings.styLuaPath)
    }

    fun resolveStyluaForSettings(
        project: Project, configurationType: StyluaConfigurationType, maybePath: String?
    ): StyLuaCli? {
        return when (configurationType) {
            StyluaConfigurationType.Auto -> if (ForemanCli.isForemanExecutableOnPath && ForemanCli.checkForForemanConfigIncludesTool(
                    project, "stylua"
                )
            ) {
                StyLuaCli("stylua")
            } else null

            StyluaConfigurationType.Manual -> if (maybePath != null) StyLuaCli(maybePath) else null
            StyluaConfigurationType.Disabled -> null
        }
    }
}

