package com.github.aleksandrsl.intellijluau

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class FileWatcherStartupActivity : ProjectActivity {

    override suspend fun execute(project: Project) {
        // Initialize the service so it listens for the setting changes and starts if needed.
        FileWatcherService.getInstance(project)
    }
}

