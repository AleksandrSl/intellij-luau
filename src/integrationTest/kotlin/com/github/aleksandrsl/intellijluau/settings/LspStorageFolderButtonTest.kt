package com.github.aleksandrsl.intellijluau.settings

import com.intellij.driver.sdk.ui.components.common.ideFrame
import com.intellij.driver.sdk.ui.components.elements.radioButton
import com.intellij.driver.sdk.ui.components.settings.settingsDialog
import com.intellij.driver.sdk.ui.xQuery
import com.intellij.driver.sdk.waitForIndicators
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.plugins.PluginConfigurator
import com.intellij.ide.starter.project.LocalProjectInfo
import com.intellij.ide.starter.runner.Starter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Path
import kotlin.time.Duration.Companion.minutes

class LspStorageFolderButtonTest {

    @Test
    fun `open LSP storage folder button is disabled when no versions are installed`() {
        Starter.newContext(
            "lsp-button-disabled", TestCase(
                IdeProductProvider.IU,
                LocalProjectInfo(
                    Path.of("src/test/resources/testProjects/empty")
                )
            ).withVersion("2026.1")
        )
            .apply {
                val pathToPlugin = System.getProperty("path.to.build.plugin")
                PluginConfigurator(this).installPluginFromPath(Path.of(pathToPlugin))
            }
            .runIdeWithDriver().useDriverAndCloseIde {
                waitForIndicators(5.minutes)
                ideFrame {
                    openSettingsDialog()
                    settingsDialog {
                        openTreeSettingsSection("Languages & Frameworks", "Luau")
                        radioButton { byVisibleText("Manage through the plugin") }.click()
                        Assertions.assertFalse(x(xQuery { byAccessibleName("Open LSP Storage Folder") }).isEnabled())
                    }
                }
            }
    }
}
