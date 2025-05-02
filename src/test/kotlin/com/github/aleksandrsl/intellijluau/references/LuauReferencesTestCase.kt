package com.github.aleksandrsl.intellijluau.references

import com.github.aleksandrsl.intellijluau.psi.LuauBinding
import com.github.aleksandrsl.intellijluau.psi.LuauClassicForStatement
import com.github.aleksandrsl.intellijluau.psi.LuauForeachStatement
import com.github.aleksandrsl.intellijluau.psi.LuauLocalFuncDefStatement
import com.github.aleksandrsl.intellijluau.psi.LuauNamedElement
import com.github.aleksandrsl.intellijluau.psi.LuauParList
import com.intellij.psi.util.parentsOfType
import com.intellij.testFramework.assertInstanceOf
import com.intellij.testFramework.fixtures.BasePlatformTestCase

const val baseTestDataPath = "src/test/testData/references"

class LuauReferencesTestCase : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return baseTestDataPath
    }

    fun `test local variable`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("localVariable.luau")
        val result = assertInstanceOf<LuauNamedElement>(referenceAtCaret.resolve())
        assertEquals("b", result.text)
    }

    fun `test local variable respect order`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("localVariable_respectOrder.luau")
        assertNull(referenceAtCaret.resolve())
    }

    // TODO (AleksandrSl 22/04/2025): Implement later
    //  I don't have a concept of global declarations at all.
    //  I have only the assignment statements and they do not contribute names.
    fun `ignore test global variable`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("globalVariable.luau")
        val result = assertInstanceOf<LuauNamedElement>(referenceAtCaret.resolve())
        assertEquals("b", result.text)
    }

    fun `test recursive functions`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("recursiveFunctions.luau")
        val result = assertInstanceOf<LuauLocalFuncDefStatement>(referenceAtCaret.resolve())
        assertEquals("a", result.name)
    }

    fun `ignore test recursive anonymous functions`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("recursiveAnonymousFunctions.luau")
        val result = assertInstanceOf<LuauBinding>(referenceAtCaret.resolve())
        assertEquals("a", result.name)
    }

    fun `ignore test shadowing`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("shadowing.luau")
        val result = assertInstanceOf<LuauLocalFuncDefStatement>(referenceAtCaret.resolve())
        assertEquals("b", result.name)
    }

    fun `ignore test function parameters`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("functionParameters.luau")
        val result = assertInstanceOf<LuauBinding>(referenceAtCaret.resolve())
        assertEquals("a", result.text)
        assertInstanceOf<LuauParList>(result.parent)
    }

    fun `ignore test local function parameters`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("localFunctionParameters.luau")
        val result = assertInstanceOf<LuauBinding>(referenceAtCaret.resolve())
        assertEquals("a", result.text)
        assertInstanceOf<LuauParList>(result.parent)
    }

    fun `ignore test anonymous function parameters`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("anonymousFunctionParameters.luau")
        val result = assertInstanceOf<LuauBinding>(referenceAtCaret.resolve())
        assertEquals("a", result.text)
        assertInstanceOf<LuauParList>(result.parent)
    }

    fun `ignore test classic for cycle`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("classicFor.luau")
        val result = assertInstanceOf<LuauBinding>(referenceAtCaret.resolve())
        assertEquals("i", result.text)
        assertTrue(result.parentsOfType<LuauClassicForStatement>().toList().isNotEmpty())
    }

    fun `ignore test foreach cycle`() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("foreach.luau")
        val result = assertInstanceOf<LuauBinding>(referenceAtCaret.resolve())
        assertEquals("k", result.text)
        assertTrue(result.parentsOfType<LuauForeachStatement>().toList().isNotEmpty())
    }

    // TODO (AleksandrSl 22/04/2025):
    //  1. Methods are not handled at all, plus self
    //  2. type packs
    //  4. Qualified names (a.b.c)- there is a question how their resolution and introduction works.
    //  Should I resolve the topmost first and the look into it's internal declarations?

    // T is not resolved
    // local format = require(Dash.format) :: (<T...>(string, T...) -> string)

}

