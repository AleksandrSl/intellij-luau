package com.github.aleksandrsl.intellijluau.folding

import com.intellij.testFramework.fixtures.BasePlatformTestCase

private const val baseTestDataPath = "src/test/testData/folding"

class LuauFoldingTestCase : BasePlatformTestCase() {

    override fun getTestDataPath(): String = baseTestDataPath

    fun testBlockFolding() {
        myFixture.testFolding("$testDataPath/block.luau")
    }

    fun testTableFolding() {
        myFixture.testFolding("$testDataPath/table.luau")
    }

    fun testBlockCommentFolding() {
        myFixture.testFolding("$testDataPath/blockComment.luau")
    }

    fun testTableArgsFolding() {
        myFixture.testFolding("$testDataPath/tableArgs.luau")
    }
}
