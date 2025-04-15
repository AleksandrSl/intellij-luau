package com.github.aleksandrsl.intellijluau.parser

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

    // Can be flaky, because it can have different results when run alone or among other tests.
    // I have a stupid fix, wrapping the expression with a function, let's see how it works out
    fun testExpressions_error() = doTest(true)

    override fun checkResult(targetDataName: String, file: PsiFile) {
        check(hasErrors(file)) {
            "Invalid file was parsed successfully: ${file.name}"
        }
        super.checkResult(targetDataName, file)
    }
}
