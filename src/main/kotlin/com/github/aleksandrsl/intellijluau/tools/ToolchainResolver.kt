package com.github.aleksandrsl.intellijluau.tools

import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.settings.StyluaConfigurationType
import com.github.aleksandrsl.intellijluau.tools.foreman.ForemanCli
import com.intellij.openapi.project.Project

object ToolchainResolver {

    fun resolveStylua(project: Project): StyLuaCli? {
        val settings = ProjectSettingsState.getInstance(project)
        return when (settings.styluaConfigurationType) {
            StyluaConfigurationType.Auto -> if (ForemanCli.isForemanExecutableOnPath && ForemanCli.checkForForemanConfigIncludesTool(project, "stylua")) {
                StyLuaCli("stylua")
            } else null
            StyluaConfigurationType.Manual -> StyLuaCli(settings.styLuaPath)
            StyluaConfigurationType.Disabled -> null
        }
    }
}

