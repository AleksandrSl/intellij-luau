package com.github.aleksandrsl.intellijluau.settings

import com.github.aleksandrsl.intellijluau.LuauBundle
import com.github.aleksandrsl.intellijluau.LuauNotifications
import com.intellij.ide.util.PropertiesComponent
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

private const val DEFAULT_SETTINGS_DISMISSED_PROPERTY = "com.github.aleksandrsl.intellijluau.defaultSettingsDismissed"

class DefaultSettingsStartupActivity : ProjectActivity {
    fun dontShowAgain(project: Project) {
        PropertiesComponent.getInstance(project).setValue(DEFAULT_SETTINGS_DISMISSED_PROPERTY, true)
    }

    override suspend fun execute(project: Project) {
        val defaultSettings = LuauDefaultSettingsState.getInstance()
        if (PropertiesComponent.getInstance(project)
                .getBoolean(DEFAULT_SETTINGS_DISMISSED_PROPERTY) || !defaultSettings.hasDefaultSettings
        ) {
            return
        }

        LuauNotifications.pluginNotifications().createNotification(
            LuauBundle.message("luau.notification.title.default.settings"),
            LuauBundle.message("luau.notification.content.default.settings"),
            NotificationType.INFORMATION
        )
            .addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.apply")) {
                val projectSettings = ProjectSettingsState.getInstance(project)
                projectSettings.loadDefaultSettings()
                dontShowAgain(project)
            })
            .addAction(NotificationAction.createSimpleExpiring(LuauBundle.message("luau.notification.actions.dont.show.for.project")) {
                dontShowAgain(project)
            })
            .notify(project)
    }
}
