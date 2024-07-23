package com.github.aleksandrsl.intellijluau.cli

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.ProcessOutput
import com.intellij.openapi.application.EDT
import com.intellij.openapi.application.readAction
import com.intellij.openapi.application.writeAction
import com.intellij.openapi.command.executeCommand
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.ReadonlyStatusHandler
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.ide.progress.withBackgroundProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun formatFiles(project: Project, files: Collection<VirtualFile>) {
        val hasReadonlyFiles =
            ReadonlyStatusHandler.getInstance(project).ensureFilesWritable(files).hasReadonlyFiles()
        if (hasReadonlyFiles) return

        // TODO (AleksandrSl 16/07/2024): Nice to handle the directories format later
        val projectService = project.service<LuauCliService>()
        projectService.coroutineScope.launch(Dispatchers.EDT) {
            withBackgroundProgress(project, "Stylua format documents") {
                @Suppress("UNCHECKED_CAST") val results = files.associateWith { file ->
                    _formatFile(project, file)
                }.filterValues { it is FormatResult.Success } as Map<VirtualFile, FormatResult.Success>

                if (results.isEmpty()) return@withBackgroundProgress

                // Interesting details, if not only the current file has been touched,
                // then you will see the undo stylua action prompt.
                // Otherwise undo will just happen.
                writeAction {
                    // TODO (AleksandrSl 16/07/2024): Should I check that file wasn't written to before I write to it?
                    executeCommand(project, "Format with Stylua") {
                        results.forEach { result ->
                            val document = FileDocumentManager.getInstance().getDocument(result.key) ?: return@forEach
                            // Despite this being called inside executeCommand, if I don't modify document content and save is run multiple time,
                            // e.g. by the shortcut and then on window unfocus. When I return to the file I only have to do one undo to revert the reformat
                            // If I don't check this, I'll have to do as many reformats as there were done.
                            // Thinking of it, it's weird because it looks like the amount of the executed commands doesn't matter.
                            // But if I do a command per each file, then it matters.
                            // I guess for the file in the editor is handled differently.
                            // It's also interesting that not all files are passed to the on save action when you unfocus the window.
                            // There is a chance this happens only for files that are modified according to the git.
                            // Lol now it didn't happen at all. Anyway, it works much better now.
                            if (StringUtil.equals(document.charsSequence, result.value.content)) return@forEach
                            document.setText(result.value.content)
                        }
                    }
                }
            }
        }
    }

    // TODO (AleksandrSl 17/07/2024): Come up with a better name
    private suspend fun _formatFile(project: Project, file: VirtualFile): FormatResult? {
        // No idea why but prettier uses Refs here
        var content: String? = null
        // PsiFile might be not committed at this point, take text from document
        readAction {
            if (!file.isValid) return@readAction
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
            return FormatResult.Success(output.stdout)
        }
        return FormatResult.StyluaError(output.stderr)
    }

    // Move this outside of the cli, because it's not cli related?
    private suspend fun formatFile(project: Project, file: VirtualFile): FormatResult? {
        // TODO (AleksandrSl 16/07/2024): Nice to check if the config is saved when I will be using it.
        val hasReadonlyFiles =
            ReadonlyStatusHandler.getInstance(project).ensureFilesWritable(listOf(file)).hasReadonlyFiles()
        if (hasReadonlyFiles) return null

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
