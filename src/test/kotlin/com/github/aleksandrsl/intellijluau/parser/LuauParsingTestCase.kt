package com.github.aleksandrsl.intellijluau.parser

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.LuauParserDefinition
import com.intellij.testFramework.ParsingTestCase

const val baseTestDataPath = "src/test/testData/parser"

class LuauParsingTestCase: ParsingTestCase("", LuauFileType.defaultExtension, LuauParserDefinition()) {
    fun testStringTemplate() {
        doTest(true)
    }

    fun testComplexTypes() {
        doTest(true)
    }

    fun testGenericsWithDefaults() {
        doTest(true)
    }

    fun testDefinitions() {
        doTest(true)
    }

    fun testCastInExpression() {
        doTest(true)
    }

    override fun getTestDataPath(): String {
        return baseTestDataPath
    }

    override fun includeRanges(): Boolean {
        return true
    }
}
