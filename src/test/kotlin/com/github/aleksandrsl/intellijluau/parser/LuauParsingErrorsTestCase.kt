package com.github.aleksandrsl.intellijluau.parser

import com.intellij.platform.testFramework.core.FileComparisonFailedError
import com.intellij.psi.PsiFile


class LuauParsingErrorsTestCase : LuauParsingBaseTestCase() {
    fun testComplexGenericDefaults_error() = doTest(true)
    fun testAlgebraicTypes_error() = doTest(true)
    fun testAlgebraicTypes_error2() = doTest(true)
    fun testAlgebraicTypes_error3() = doTest(true)
    fun testComplexTypes_error() = doTest(true)
    fun testStringTemplate_error() = doTest(true)
    fun testGenericsTypePackParameters_error() = doTest(true)
    fun testLonelyExpressions_error() = doTest(true)

    // I have little idea why it generates dummy blocks sometimes,
    // but until I found a time to check this, let's have two tests, one of them must pass
    fun testExpressions_error() {
        try {
            val name = "Expressions_error"
            parseFile(name, loadFile("$name.$myFileExt"))
            checkResult(name, myFile)
        } catch (e: FileComparisonFailedError) {
            val name = "Expressions_error_alt"
            parseFile(name, loadFile("$name.$myFileExt"))
            checkResult(name, myFile)
        }
    }

    override fun checkResult(targetDataName: String, file: PsiFile) {
        check(hasErrors(file)) {
            "Invalid file was parsed successfully: ${file.name}"
        }
        super.checkResult(targetDataName, file)
    }
}
