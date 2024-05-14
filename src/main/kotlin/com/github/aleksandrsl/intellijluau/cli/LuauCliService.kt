package com.github.aleksandrsl.intellijluau.cli

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import kotlinx.coroutines.CoroutineScope

@Service(Service.Level.PROJECT)
class LuauCliService(
    private val project: Project,
    val coroutineScope: CoroutineScope
) {

}
