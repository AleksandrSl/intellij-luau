package com.github.aleksandrsl.intellijluau.cli

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.OSProcessHandler
import com.intellij.openapi.application.readAction
import com.intellij.openapi.application.writeAction
import com.intellij.openapi.command.executeCommand
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Path
import kotlin.io.path.pathString


private val LOG = logger<StyLuaCli>()

/**
 * Interact with external `StyLua` process.
 */
class StyLuaCli(private val styLuaExecutablePath: Path) {

    // TODO (AleksandrSl 17/07/2024): Come up with a better name
    private suspend fun _formatFile(project: Project, file: VirtualFile): FormatResult? {
        // No idea why but prettier uses Refs here
        var content: String? = null
        // PsiFile might be not committed at this point, take text from document
        readAction {
            if (!file.isValid || !file.isWritable) return@readAction
            // Prettier uses Document document = PsiDocumentManager.getInstance(project).getDocument(currentFile); instead of FileDocumentManager, why?
            // Elm plugin uses text, prettier get charSequence and converts it to the string. I get the prettier way may be more performant?
            // TODO (AleksandrSl 16/07/2024): Should I care for line endings as it's done in prettier?
            content = FileDocumentManager.getInstance().getDocument(file)?.charsSequence.toString()
        }
        // TODO (AleksandrSl 16/07/2024): Check for ignored files and don't format them
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
            addParameter(file.path)
            addParameter("-")
        }

        try {
            val output = withContext(Dispatchers.IO) {
                val handler = CapturingProcessHandler(commandLine)
                handler.processInput.use { it.write(stdin.toByteArray(charset)) }
                LOG.debug(commandLine.commandLineString)
                handler.runProcess()
            }
            LOG.debug("Test ${output.stdout}, ${output.exitCode}")
            if (output.checkSuccess(LOG)) {
                return FormatResult.Success(output.stdout)
            }
            return FormatResult.StyluaError(output.stderr)
        } catch (e: Exception) {
            return FormatResult.StyluaError(e.message ?: "Unknown error")
        }
    }

    fun createOsProcessHandler(project: Project, file: VirtualFile): OSProcessHandler {
        val charset = Charsets.UTF_8;
        val commandLine = GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            // Seems that it's null by default
            withWorkDirectory(project.basePath)
            withCharset(charset)
            withExePath(styLuaExecutablePath.pathString)
            addParameter("--stdin-filepath")
            addParameter(file.path)
            addParameter("-")
        }

        return OSProcessHandler(commandLine)
    }

    // Move this outside of the cli, because it's not cli related?
    suspend fun formatFile(project: Project, file: VirtualFile): FormatResult? {
        // TODO (AleksandrSl 16/07/2024): Nice to check if the config is saved when I will be using it.
        //  What config I was talking about?
        val result = _formatFile(project, file)

        if (result is FormatResult.Success) {
            // TODO (AleksandrSl 14/07/2024): WriteCommandAction.runWriteCommandAction() is used in docs and in prettier plugin.
            //  They don't use extra execute command inside. Is it applied by default?
            //  Check how prettier works on save. If several files are modifed and you save does it reformats all the files?
            //  If you roll back does it roll back all the files with one command? If it doesn't, does it have a command for each file change as I have?
            //  Prettier:
            //   If you roll back it roll backs all the files that were formatted
            //   It reformats all the modified files at once which were not saved yet.
            writeAction {
                executeCommand(project, "Format with Stylua") {
                    // No idea if I can save the document to a variable to reference it safer later, I guess I can. So a place for future refactoring
                    FileDocumentManager.getInstance().getDocument(file)?.setText(result.content)
                }
            }
        }
        return result
    }

    suspend fun formatDocument(project: Project): FormatResult? {
        val fileEditorManager = FileEditorManager.getInstance(project)
        val files: Array<VirtualFile> = fileEditorManager.selectedFiles
        if (files.isEmpty()) {
            return null
        }
        return formatFile(project, files[0])
    }

    sealed class FormatResult(val msg: String) {
        class Success(val content: String) : FormatResult("Ok")
        class StyluaError(msg: String) : FormatResult("Stylua failed to run \n$msg")
    }

    fun queryVersion(): String {
        val firstLine = CapturingProcessHandler(GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withCharset(Charsets.UTF_8)
            withExePath(styLuaExecutablePath.pathString)
            addParameter("--version")
//                 TODO: Do lazy reading?
        }).runProcess().stdoutLines.first()
        return firstLine
    }
}
