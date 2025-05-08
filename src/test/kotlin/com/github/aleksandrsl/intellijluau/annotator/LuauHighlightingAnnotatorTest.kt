/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package com.github.aleksandrsl.intellijluau.annotator

import com.intellij.testFramework.fixtures.BasePlatformTestCase


const val baseTestDataPath = "src/test/testData/annotator"

class LuauHighlightingAnnotatorTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return baseTestDataPath
    }

    fun `test type parameters`() {
        myFixture.configureByFile("typeParameters.luau")
        myFixture.checkHighlighting(false, true, true, false)
    }

    fun `test stdlib`() {
        myFixture.configureByFile("stdlib.luau")
        myFixture.checkHighlighting(false, true, true, false)
    }

    fun `test attributes`() {
        myFixture.configureByFile("attributes.luau")
        myFixture.checkHighlighting(false, true, true, false)
    }

    fun `test double curly braces in interpolation`() {
        myFixture.configureByFile("doubleCurlyBracesInInterpolation.luau")
        myFixture.checkHighlighting(false, true, true, false)
    }
}
