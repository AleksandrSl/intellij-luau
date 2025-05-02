package com.github.aleksandrsl.intellijluau.completion

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase


const val baseTestDataPath = "src/test/testData/completion"

class LuauCompletionTestCase : BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return baseTestDataPath
    }

    fun `test keywords completion at the beginning of a line`() {
        myFixture.configureByFiles("keywords.luau")
        myFixture.complete(CompletionType.BASIC)
        val lookupElementStrings = myFixture.lookupElementStrings
        assertNotNull(lookupElementStrings)
        assertSameElements(
            lookupElementStrings as Collection<String>,
            "local",
            "function",
            "while",
            "for",
            "export",
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
