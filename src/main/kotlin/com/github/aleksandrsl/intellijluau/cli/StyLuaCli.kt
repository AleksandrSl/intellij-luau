package com.github.aleksandrsl.intellijluau.cli

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.ProcessOutput
import com.intellij.openapi.application.readAction
import com.intellij.openapi.application.writeAction
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.ReadonlyStatusHandler
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.writeText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
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

    suspend fun formatDocument(project: Project): FormatResult? {
        val fileEditorManager = FileEditorManager.getInstance(project)
        val files: Array<VirtualFile> = fileEditorManager.selectedFiles
        if (files.isEmpty()) {
            return null
        }
        val currentFile = files[0]

        val hasReadonlyFiles = ReadonlyStatusHandler.getInstance(project).ensureFilesWritable(listOf(currentFile))
            .hasReadonlyFiles()
        if (hasReadonlyFiles) return null

        var content: String? = null
        // PsiFile might be not committed at this point, take text from document
        readAction {
            // Maybe it's not that great for performance, as charSequence but elm plugin uses text
            content = FileDocumentManager.getInstance().getDocument(currentFile)?.text
        }
        val stdin = content
        if (stdin.isNullOrEmpty()) return null
        // Consider using ExecUtil
        val charset = Charsets.UTF_8;
        val commandLine = GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            // Seems that it's null by default
            withWorkDirectory(project.basePath)
            withCharset(charset)
            withExePath(styLuaExecutablePath.pathString)
            addParameter("--stdin-filepath")
            addParameter(currentFile.path)
            addParameter("-")
        }

        val output = withContext(Dispatchers.IO) {
            val process = commandLine.createProcess()
            val stdinStream = process.outputStream
            val writer = OutputStreamWriter(stdinStream, charset)
            try {
                writer.write(stdin)
            } finally {
                writer.flush()
                writer.close()
            }
            val handler = CapturingProcessHandler(process, charset, commandLine.commandLineString)
            LOG.warn(commandLine.commandLineString)
            handler.runProcess()
        }
        if (output.checkSuccess(LOG)) {
            writeAction {
                currentFile.writeText(output.stdout)
            }
            return FormatResult.Success()
        }
        return FormatResult.StyluaError(output.stderr)
    }

    sealed class FormatResult(val msg: String, val cause: Throwable? = null) {
        class Success: FormatResult("Ok")
        class StyluaError(msg: String): FormatResult("Stylua failed to run \n$msg")
    }

    fun queryVersion(): String {
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
