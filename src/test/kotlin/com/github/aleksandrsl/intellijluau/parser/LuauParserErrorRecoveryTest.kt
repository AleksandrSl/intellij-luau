package com.github.aleksandrsl.intellijluau.parser

import com.github.aleksandrsl.intellijluau.psi.LuauListArgs
import com.github.aleksandrsl.intellijluau.psi.LuauParList
import com.github.aleksandrsl.intellijluau.psi.LuauTableConstructor
import com.github.aleksandrsl.intellijluau.psi.LuauTemplateStringExpr
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.parents
import java.io.IOException

class LuauParserRecoveryTest : LuauParsingBaseTestCase("recovery") {

    fun testFunctionCall() = doTest(LuauListArgs::class.java)
    fun testTableConstructor() = doTest(LuauTableConstructor::class.java)
    fun testTableConstructor2() = doTest(LuauTableConstructor::class.java)
    fun testStringTemplate() = doTest(LuauTemplateStringExpr::class.java)
    fun testStringTemplate2() = doTest(LuauTemplateStringExpr::class.java)
    fun testStringTemplate3() = doTest(LuauTemplateStringExpr::class.java)
    fun testFunctionDeclaration() = doTest(LuauParList::class.java)

    private fun doTest(target: Class<out PsiElement>) {
        val name = testName
        try {
            parseFile(name, loadFile("$name.$myFileExt"))
            checkResult(name, myFile, target)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun checkResult(targetDataName: String, file: PsiFile, target: Class<out PsiElement>) {
        when (val result = hasErrorsInSpecificElementType(file, target)) {
            ErrorRecoveryResult.Success -> {}
            ErrorRecoveryResult.ErrorNotFound -> fail("No error found in file $targetDataName")
            is ErrorRecoveryResult.UnexpectedError -> fail("Unexpected error found in file $targetDataName: ${result.trace}")
        }
        super.checkResult(targetDataName, file)
    }

    private fun hasErrorsInSpecificElementType(file: PsiFile, targetType: Class<out PsiElement>): ErrorRecoveryResult {
        var requiredErrorFound = false
        var unexpectedErrorTrace: String? = null
        file.accept(object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {

                if (element is PsiErrorElement) {
                    unexpectedErrorTrace = element.parents(true).map { it.node.toString() }.joinToString(",")
                    return
                }

                if (targetType.isInstance(element)) {
                    element.acceptChildren(object : PsiElementVisitor() {
                        override fun visitElement(child: PsiElement) {
                            if (child is PsiErrorElement) {
                                requiredErrorFound = true
                            } else {
                                child.acceptChildren(this)
                            }
                        }
                    })
                } else {
                    element.acceptChildren(this)
                }
            }
        })
        if (!requiredErrorFound) {
            return ErrorRecoveryResult.ErrorNotFound
        }

        if (unexpectedErrorTrace != null) {
            return ErrorRecoveryResult.UnexpectedError(unexpectedErrorTrace)
        }
        return ErrorRecoveryResult.Success
    }


//    fun testMissingEndInIfStatement() = doTest("""
//        if x > 0 then
//            y = 1
//        -- missing end
//        z = 2
//    """)
//
//    fun testIncompleteExpression() = doTest("""
//        local x = 1 +    -- incomplete expression
//        local y = 2
//    """)
//
//    fun testUnmatchedParentheses() = doTest("""
//        local x = (1 + 2  -- missing closing parenthesis
//        local y = 3
//    """)
//
//    fun testIncompleteFunction() = doTest("""
//        function test(x, y
//            return x + y  -- missing closing parenthesis and end
//        local z = 1
//    """)
//
//    fun testInvalidBinaryOperator() = doTest("""
//        local x = 1 ++ 2  -- invalid operator
//        local y = 3
//    """)
//
//    fun testMissingDoInWhileLoop() = doTest("""
//        while true      -- missing do
//            x = x + 1
//        end
//    """)
//
//    fun testIncompleteForLoop() = doTest("""
//        for i = 1, 10   -- missing do
//            print(i)
//        end
//    """)
//
//    fun testBrokenTypeAnnotation() = doTest("""
//        local x: number | -- incomplete type union
//        local y = 1
//    """)
//
//    fun testMultipleErrors() = doTest("""
//        if x > 0 then
//            local t = {
//                a = 1,
//            print(t.a  -- missing closing brace and parenthesis
//        -- missing end
//        local z = 1
//    """)
//
//    fun testIncompleteAttributeDeclaration() = doTest("""
//        @[foo(  -- missing closing parenthesis and bracket
//        function test()
//        end
//    """)
//
//
//    fun testMalformedCompoundAssignment() = doTest("""
//        x += * 2   -- invalid expression after operator
//        y = 1
//    """)
//
//    fun testIncompleteTypeFunction() = doTest("""
//        type function F<T>()   -- missing return type and end
//        local x = 1
//    """)
}

sealed class ErrorRecoveryResult {
    data object Success : ErrorRecoveryResult()
    data object ErrorNotFound : ErrorRecoveryResult()
    data class UnexpectedError(val trace: String) : ErrorRecoveryResult()
}
