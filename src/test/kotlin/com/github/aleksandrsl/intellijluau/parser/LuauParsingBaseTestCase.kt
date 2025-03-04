package com.github.aleksandrsl.intellijluau.parser

import com.github.aleksandrsl.intellijluau.LuauFileType
import com.github.aleksandrsl.intellijluau.LuauParserDefinition
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.testFramework.ParsingTestCase

const val baseTestDataPath = "src/test/testData/parser"

open class LuauParsingBaseTestCase : ParsingTestCase("", LuauFileType.defaultExtension, LuauParserDefinition()) {
    protected fun hasErrors(file: PsiFile): Boolean {
        var hasErrors = false
        file.accept(object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                if (element is PsiErrorElement) {
                    hasErrors = true
                    return
                }
                element.acceptChildren(this)
            }
        })
        return hasErrors
    }

    override fun getTestDataPath(): String {
        return baseTestDataPath
    }

    override fun includeRanges(): Boolean {
        return true
    }
}
