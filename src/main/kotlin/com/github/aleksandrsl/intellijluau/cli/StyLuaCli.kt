package com.github.aleksandrsl.intellijluau.cli

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.ProcessOutput
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.Path
import kotlin.io.path.pathString


private val LOG = logger<StyLuaCli>()

/**
 * Interact with external `StyLua` process.
 */
class StyLuaCli(private val styLuaExecutablePath: Path) {

    fun formatDocumentOnDisk(project: Project): ProcessOutput? {
        val fileEditorManager = FileEditorManager.getInstance(project)
        val files: Array<VirtualFile> = fileEditorManager.selectedFiles
        if (files.isNotEmpty()) {
            val currentFile = files[0]
            // Consider using ExecUtil
            //TODO I need to handle errors here, it seems
            return CapturingProcessHandler(GeneralCommandLine().apply {
                withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
                // Seems that it's null by default
                withWorkDirectory(project.basePath)
                withCharset(Charsets.UTF_8)
                withExePath(styLuaExecutablePath.pathString)
                addParameter(currentFile.path)
            }).runProcess()
        }
        return null
    }

    fun formatDocument(project: Project): ProcessOutput? {
        val fileEditorManager = FileEditorManager.getInstance(project)
        val files: Array<VirtualFile> = fileEditorManager.selectedFiles
        if (files.isNotEmpty()) {
            val currentFile = files[0]
            // Consider using ExecUtil
            //TODO I need to handle errors here, it seems
            return CapturingProcessHandler(GeneralCommandLine().apply {
                withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
                // Seems that it's null by default
                withWorkDirectory(project.basePath)
                withCharset(Charsets.UTF_8)
                withExePath(styLuaExecutablePath.pathString)
                addParameter(currentFile.path)
            }).runProcess()
        }
        return null
    }

    fun queryVersion(project: Project): String {
        val firstLine =
            CapturingProcessHandler(GeneralCommandLine().apply {
                withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
                withCharset(Charsets.UTF_8)
                withExePath(styLuaExecutablePath.pathString)
                addParameter("--version")
//                 TODO: Do lazy reading?
            }).runProcess().stdoutLines.first()
        return firstLine
    }
}
