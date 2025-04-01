package com.github.aleksandrsl.intellijluau

import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class FileWatcherStartupActivity : ProjectActivity {

    override suspend fun execute(project: Project) {
        if (ProjectSettingsState.getInstance(project).shouldGenerateSourceMapsFromRbxp) {
            project.getService(FileWatcherService::class.java).start()
        }
    }
}

