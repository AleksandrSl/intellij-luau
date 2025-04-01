package com.github.aleksandrsl.intellijluau

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class LuauPluginDisposable : Disposable {
    override fun dispose() {
    }

    companion object {
        fun getInstance(project: Project): Disposable {
            return project.getService(LuauPluginDisposable::class.java)
        }
    }
}
