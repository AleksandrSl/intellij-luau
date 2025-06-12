package com.github.aleksandrsl.intellijluau.formatter

import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.cli.StyLuaCli
import com.github.aleksandrsl.intellijluau.psi.LuauFile
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.codeInsight.actions.ReformatCodeProcessor
import com.intellij.execution.ExecutionException
import com.intellij.execution.process.CapturingProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.formatting.service.AsyncDocumentFormattingService
import com.intellij.formatting.service.AsyncFormattingRequest
import com.intellij.formatting.service.FormattingService
import com.intellij.openapi.command.CommandProcessor
import com.intellij.psi.PsiFile
import com.intellij.psi.formatter.FormatterUtil
import java.io.File
import java.util.*

class StyluaFormatterService : AsyncDocumentFormattingService() {
    override fun getFeatures(): Set<FormattingService.Feature> = FEATURES

    override fun canFormat(file: PsiFile): Boolean {
        val formattingReason = getFormattingReason()
        return (file is LuauFile && ProjectSettingsState.getInstance(file.project)
            .let { it.runStyLuaInsteadOfFormatter || it.disableBuiltinFormatter }
                && (formattingReason == FormattingReason.ReformatCode || formattingReason == FormattingReason.ReformatCodeBeforeCommit))
    }

    override fun createFormattingTask(request: AsyncFormattingRequest): FormattingTask? {
        val context = request.context
        val project = context.project
        val file = context.virtualFile ?: return null
        val settings = ProjectSettingsState.getInstance(project)
        // This is a hack, maybe implementing LanguageFormattingRestriction is better,
        if (settings.disableBuiltinFormatter) return null
        val tool = File(settings.styLuaPath)

        try {
            val handler = StyLuaCli(tool.toPath()).createOsProcessHandler(project, file)

            return object : FormattingTask {
                override fun run() {
                    handler.processInput.use { it.write(request.documentText.toByteArray()) }
                    // Heavy inspired by the https://github.com/JetBrains/intellij-community/blob/idea/243.26053.27/plugins/sh/core/src/com/intellij/sh/formatter/ShExternalFormatter.java
                    handler.addProcessListener(object : CapturingProcessAdapter() {
                        override fun processTerminated(event: ProcessEvent) {
                            val exitCode = event.exitCode
                            if (exitCode == 0) {
                                request.onTextReady(output.stdout)
                            } else {
                                request.onError("Failed to format", output.stderr)
                            }
                        }
                    })
                    handler.startNotify()
                }

                override fun cancel(): Boolean {
                    handler.destroyProcess()
                    return true
                }

                override fun isRunUnderProgress(): Boolean {
                    return true
                }
            }
        } catch (e: ExecutionException) {
            request.onError("Failed to format", e.message ?: "Unknown error")
            return null
        }
    }

    override fun getNotificationGroupId(): String = LuauNotifications.GROUP_ID

    override fun getName(): String = "Stylua"

    companion object {
        private val FEATURES: Set<FormattingService.Feature> = EnumSet.noneOf(FormattingService.Feature::class.java)

        private enum class FormattingReason {
            ReformatCode,
            ReformatCodeBeforeCommit,
            Implicit
        }

        private fun getFormattingReason(): FormattingReason =
            when (CommandProcessor.getInstance().currentCommandName) {
                ReformatCodeProcessor.getCommandName() -> FormattingReason.ReformatCode
                FormatterUtil.getReformatBeforeCommitCommandName() -> FormattingReason.ReformatCodeBeforeCommit
                else -> FormattingReason.Implicit
            }
    }
}

