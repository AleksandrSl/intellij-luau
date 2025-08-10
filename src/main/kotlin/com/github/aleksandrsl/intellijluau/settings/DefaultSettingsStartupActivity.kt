package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.github.aleksandrsl.intellijluau.showProjectNotification
import com.github.aleksandrsl.intellijluau.util.hasLuauFiles
import com.intellij.ide.util.PropertiesComponent
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

private const val DEFAULT_SETTINGS_DISMISSED_PROPERTY = "com.github.aleksandrsl.intellijluau.defaultSettingsDismissed"

// RunOnceUtil is similar to what I have, but I do want to show again it if it weren't closed manually.
// If there is a different need, this utility can be used.
class DefaultSettingsStartupActivity : ProjectActivity {

    override suspend fun execute(project: Project) {
        val defaultSettings = LuauDefaultSettingsState.getInstance()
        // Once the default settings are applied, DEFAULT_SETTINGS_DISMISSED_PROPERTY will be set,
        // so we won't be spamming with this suggestion
        // even though I don't check that current settings are the same as the default.
        if (PropertiesComponent.getInstance(project)
                .getBoolean(DEFAULT_SETTINGS_DISMISSED_PROPERTY) || !defaultSettings.hasDefaultSettings || !project.hasLuauFiles()
        ) {
            return
        }

        LuauNotifications.pluginNotifications().showProjectNotification(
            LuauBundle.message("luau.notification.title.default.settings"),
            LuauBundle.message("luau.notification.content.default.settings"),
            NotificationType.INFORMATION,
            project
        ) {
            addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.apply")) {
                val projectSettings = ProjectSettingsState.getInstance(project)
                projectSettings.loadDefaultSettings()
                dontSuggestDefaultSettingsAgain(project)
            }).addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.dont.show.for.project")) {
                dontSuggestDefaultSettingsAgain(project)
            })
        }
    }
}

internal fun dontSuggestDefaultSettingsAgain(project: Project) {
    PropertiesComponent.getInstance(project).setValue(DEFAULT_SETTINGS_DISMISSED_PROPERTY, true)
}
