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

    fun `test completion inside function should include function parameter`() {
        myFixture.configureByFiles("functionParameters.luau")
        myFixture.complete(CompletionType.BASIC)
        val lookupElementStrings = myFixture.lookupElementStrings
        assertNotNull(lookupElementStrings)
        assertContainsElements(
            lookupElementStrings as Collection<String>,
            listOf("c", "d", "foo", "bar")
        )
        assertDoesntContain(lookupElementStrings, listOf("a", "b"))
    }

    fun `test completion of nested functions`() {
        myFixture.configureByFiles("nestedFunctions.luau")
        myFixture.complete(CompletionType.BASIC)
        val lookupElementStrings = myFixture.lookupElementStrings
        assertNotNull(lookupElementStrings)
        assertContainsElements(
            lookupElementStrings as Collection<String>,
            listOf("methodArg1", "methodArg2", "localFuncArg1", "localFuncArg2", "closureArg1", "closureArg2", "funcArg1", "funcArg2", "foo", "baz", "a", "bar")
        )
        assertDoesntContain(lookupElementStrings, listOf("outerArg1", "outerArg2", "closure"))
    }

    fun `test completion should not include local declaration after the current one`() {
        myFixture.configureByFiles("localDeclarations.luau")
        myFixture.complete(CompletionType.BASIC)
        val lookupElementStrings = myFixture.lookupElementStrings
        assertNotNull(lookupElementStrings)
        assertContainsElements(
            lookupElementStrings as Collection<String>,
            listOf("localVar1", "localFunc1", "globalFunc1", "globalFunc2")
        )
        assertDoesntContain(lookupElementStrings, listOf("localFunc2", "localVar2"))
    }
}
