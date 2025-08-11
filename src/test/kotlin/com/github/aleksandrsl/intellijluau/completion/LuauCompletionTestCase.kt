package com.github.aleksandrsl.intellijluau.completion

import com.github.aleksandrsl.intellijluau.settings.LspConfigurationType
import com.github.aleksandrsl.intellijluau.settings.ProjectSettingsState
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.BasePlatformTestCase


const val baseTestDataPath = "src/test/testData/completion"

class LuauCompletionTestCase : BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return baseTestDataPath
    }

    override fun setUp() {
        super.setUp()
        ProjectSettingsState.getInstance(project).state.lspConfigurationType = LspConfigurationType.Disabled
    }

    fun `test keywords completion at the beginning of a line`() {
        myFixture.configureByFiles("keywords.luau")
        myFixture.complete(CompletionType.BASIC)
        val lookupElementStrings = myFixture.lookupElementStrings
        assertNotNull(lookupElementStrings)
        assertSameElements(
            lookupElementStrings as Collection<String>,
            "f", // Wrapper function declared in the file. Is it possible to exclude reference contribution?
            "local",
            "function",
            "local function",
            "while",
            "for",
            "export type",
            "type",
            "return",
            "do",
            "repeat",
            "if"
        )
    }

    fun `test keywords completion after inside statement`() {
        myFixture.configureByFiles("keywordsInsideStatement.luau")
        myFixture.complete(CompletionType.BASIC)
        val lookupElementStrings = myFixture.lookupElementStrings
        assertNotNull(lookupElementStrings)
        assertSameElements(
            lookupElementStrings as Collection<String>,
            listOf()
        )
    }
}
