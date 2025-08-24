package com.github.aleksandrsl.intellijluau.tools

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
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import java.io.IOException


private val LOG = logger<StyLuaCli>()

/**
 * Interact with external `StyLua` process.
 */
class StyLuaCli(styLuaExecutablePathString: String) : ExternalToolCli(styLuaExecutablePathString) {

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

        return runFormatProcess(project, file, stdin.toByteArray())
    }

    suspend fun runFormatProcess(project: Project, file: VirtualFile, content: ByteArray): FormatResult {
        var handler: OSProcessHandler? = null
        try {
            // Ensure that I call withContext(Dispatchers.IO) as closer to the IO as possible. https://plugins.jetbrains.com/docs/intellij/coroutine-dispatchers.html#io-dispatcher
            val output = withContext(Dispatchers.IO) {

                // No idea why sh formatter creates the process outside the task, but I can't do that now.
                // I need non edt context to resolve stylua. Rust does a similar thing check if rustfmt has to be installed. So I guess it's fine to have the stuff here.
                // Maybe they create a handler outside to be able to cancel it? I see rust uses ProgressIndicator for that, which is obsolete.
                // Out of all plugins in the plugin repository, only terraform has something as a reference, using modern apis. Let's follow it and see.
                // I also took a look at python, but their code is even weirder. They just return true from cancel every time not cancelling anything.
                // Funny, it's not only me who are struggling with "Pipe has been ended" - https://youtrack.jetbrains.com/issue/PY-71048/The-pipe-has-been-ended-exception-when-formatting-with-Black
                handler = CapturingProcessHandler(
                    createBaseCommandLine(
                        listOf("--stdin-filepath", file.path, "-"), workingDirectory = project.basePath
                    )
                )
                try {
                    // Seems like writing can be done both before the process starts, but it's actually performed after the start, which makes sense.
                    // If the process fails before that, we catch only the pipe has been closed error
                    handler.processInput.use { it.write(content) }
                } catch (e: IOException) {
                    if (e.message == "The pipe has been ended") {
                        // This means that the process has already terminated and will error anyway when runProcess is called.
                        // I tried a couple other things, like writing to processInput later and trying to check if the process is alive before that, but it didn't work.
                        // Looks like the process is terminated a bit after since it's an async thing.
                        // Let's just ignore it, so we can show the user the real error.
                    } else {
                        throw e
                    }
                }
                // Timeout just in case the stuff above is not correct 100% of the time.
                handler.runProcess(2000)
            }
            return if (output.exitCode == 0) {
                FormatResult.Success(output.stdout)
            } else {
                FormatResult.StyluaError(output.stderr)
            }
        } catch (e: Exception) {
            return FormatResult.StyluaError(e.message ?: "Unknown error")
        } finally {
            withContext(NonCancellable) {
                handler?.destroyProcess()
            }
        }
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
        class StyluaError(msg: String) : FormatResult("Stylua failed to run. \n$msg")
    }

    fun queryVersion(project: Project): Result<String> {
        // TODO: Do lazy reading?
        val result = CapturingProcessHandler(
            createBaseCommandLine(
                listOf("--version"),
                workingDirectory = project.basePath
            )
        ).runProcess()
        if (result.exitCode != 0) {
            return Result.failure(IllegalStateException("Failed to get stylua version: ${result.stderrLines}"))
        } else {
            val stdout = result.stdoutLines
            val firstLine = stdout.firstOrNull()
            if (firstLine?.matches(Regex("stylua \\d+\\.\\d+\\.\\d+")) == true) {
                return Result.success(firstLine)
            }
            return Result.failure(IllegalStateException("Failed to get stylua version: $stdout"))
        }
    }
}
