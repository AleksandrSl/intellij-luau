package com.github.aleksandrsl.intellijluau.structureView

import com.intellij.testFramework.PlatformTestUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.ui.tree.TreeUtil

const val baseTestDataPath = "src/test/testData/structureView"

class LuauStructureViewTestCase : BasePlatformTestCase() {
    override fun getTestDataPath() = baseTestDataPath

    fun testBase() {
        // Lsp server will fail to start, I tried to deregister it via
        //  ```
        //  myFixture.project.extensionArea.unregisterExtensionPoint("com.intellij.platform.lsp.serverSupportProvider")
        //  ExtensionTestUtil.maskExtensions(ExtensionPointName("platform.lsp.serverSupportProvider"), listOf(
        //    TestLspServerSupportProvider::class.java,
        //  ), myFixture.projectDisposable)
        //  ```
        //  But it doesn't work
        myFixture.configureByFile("base.luau")
        myFixture.testStructureView {
            TreeUtil.expandAll(it.tree)
            PlatformTestUtil.assertTreeEqual(it.tree, """-base.luau
 b
 c
 a""")
        }
    }
}
