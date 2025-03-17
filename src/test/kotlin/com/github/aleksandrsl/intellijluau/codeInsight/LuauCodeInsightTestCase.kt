package com.github.aleksandrsl.intellijluau.codeInsight

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase


const val baseTestDataPath = "src/test/testData/codeInsight"

class LuauCodeInsightTestCase : BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return baseTestDataPath
    }

    fun testRenameFunction() {
        myFixture.configureByFiles("renameFunction.lua")
        myFixture.renameElementAtCaret("renamed")
        myFixture.checkResultByFile("renameFunction.lua", "renameFunctionAfter.lua", false)
    }

    fun testRenameLocalVariable() {
        myFixture.configureByFiles("renameLocalVariable.lua")
        myFixture.renameElementAtCaret("other")
        myFixture.checkResultByFile("renameLocalVariable.lua", "renameLocalVariableAfter.lua", false)
    }

    fun testFormatter() {
        myFixture.configureByFiles("formatter.luau")
        WriteCommandAction.writeCommandAction(project).run<RuntimeException> {
            CodeStyleManager.getInstance(
                project
            ).reformatText(
                myFixture.file, listOf(myFixture.file.textRange)
            )
        }
        myFixture.checkResultByFile("formatter.luau", "formatterAfter.luau", false)
    }
}
