package com.github.aleksandrsl.intellijluau.parser

import com.intellij.psi.PsiFile


class LuauParsingTestCase : LuauParsingBaseTestCase() {
    fun testStringTemplate() = doTest(true)
    fun testComplexTypes() = doTest(true)
    fun testGenericsWithDefaults() = doTest(true)
    fun testDefinitions() = doTest(true)
    fun testCastInExpression() = doTest(true)
    fun testAlgebraicTypesWithLeadingSymbol() = doTest(true)
    fun testAlgebraicTypes() = doTest(true)
    fun testTypeDeclarations() = doTest(true)
    fun testAttributes() = doTest(true)
    fun testAttributesFuture() = doTest(true)
    fun testGenericsTypePackParameters() = doTest(true)
    fun testLonelyExpressions() = doTest(true)
    fun testEmptyGeneric() = doTest(true)
    fun testReturnTypes() = doTest(true)
    fun testTables() = doTest(true)
    fun testConditionals() = doTest(true)
    fun testFunctionTypes() = doTest(true)
    fun testReadWriteTables() = doTest(true)
    fun testTypeFunctions() = doTest(true)
    fun testStringTemplateEscapes() = doTest(true)
    fun testTypeWithRecovery() = doTest(true)
    fun testGenericTypePackReturn() = doTest(true)
    fun testTypeCastInsideExpr() = doTest(true)
    fun testFunctionDefinitions() = doTest(true)

    override fun checkResult(targetDataName: String, file: PsiFile) {
        // I took a peek at how they do the tests in rust https://github.com/search?q=repo%3Aintellij-rust/intellij-rust%20hasError&type=code,
        // and the super call is positioned differently in checkResult for valid and invalid files.
        // I guess for valid files all other errors should go first so the super call is first,
        // or it is a bug and I just copied it, lol.
        super.checkResult(targetDataName, file)
        check(!hasErrors(file)) {
            "Valid file was parsed with errors: ${file.name}"
        }
    }
}
