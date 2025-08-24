package com.github.aleksandrsl.intellijluau.formatter

import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.psi.LuauFile
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.github.aleksandrsl.intellijluau.tools.StyLuaCli.FormatResult
import com.github.aleksandrsl.intellijluau.tools.ToolchainResolver
import com.intellij.codeInsight.actions.ReformatCodeProcessor
import com.intellij.formatting.service.AsyncDocumentFormattingService
import com.intellij.formatting.service.AsyncFormattingRequest
import com.intellij.formatting.service.FormattingService
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.runBlockingCancellable
import com.intellij.psi.PsiFile
import com.intellij.psi.formatter.FormatterUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

private val LOG = logger<StyluaFormatterService>()

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

        // Has been heavily inspired by the https://github.com/JetBrains/intellij-community/blob/idea/243.26053.27/plugins/sh/core/src/com/intellij/sh/formatter/ShExternalFormatter.java
        // Now it's inspired https://github.com/JetBrains/intellij-plugins/blob/c9ee79f92779c8f3c3f680961d19ebf11d5e1b93/terraform/src/org/intellij/terraform/hcl/formatter/TfAsyncFormattingService.kt
        return object : FormattingTask {
            private var job: Job? = null

            @Suppress("UnstableApiUsage")
            override fun run() {
                try {
                    runBlockingCancellable {
                        val stylua = ToolchainResolver.resolveStylua(project)
                            ?: return@runBlockingCancellable request.onTextReady(request.documentText)
                        job = launch {
                            val result = stylua.runFormatProcess(project, file, request.documentText.toByteArray())
                            when (result) {
                                is FormatResult.Success -> request.onTextReady(result.content)
                                is FormatResult.StyluaError -> request.onError("Failed to format", result.msg)
                            }
                        }
                    }
                } catch (exception: Exception) {
                    LOG.warn(exception)
                    request.onError("Failed to format", exception.localizedMessage)
                }
            }

            override fun cancel(): Boolean {
                job?.cancel()
                return true
            }

            override fun isRunUnderProgress(): Boolean {
                return true
            }
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

