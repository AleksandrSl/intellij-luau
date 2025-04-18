package com.github.aleksandrsl.intellijluau.references

import com.github.aleksandrsl.intellijluau.psi.LuauNamedElement
import com.intellij.testFramework.assertInstanceOf
import com.intellij.testFramework.fixtures.BasePlatformTestCase

const val baseTestDataPath = "src/test/testData/references"

class LuauReferencesTestCase: BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return baseTestDataPath
    }

    fun testPrimitiveVariable() {
        val referenceAtCaret =
            myFixture.getReferenceAtCaretPositionWithAssertion("primitiveVariable.luau")
        val resolvedSimpleProperty = assertInstanceOf<LuauNamedElement>(referenceAtCaret.resolve())
        assertEquals("b", resolvedSimpleProperty.text)
    }
}

