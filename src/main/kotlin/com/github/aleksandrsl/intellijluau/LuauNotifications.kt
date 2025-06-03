package com.github.aleksandrsl.intellijluau

import com.intellij.notification.Notification
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object LuauNotifications {
    const val GROUP_ID = "Luau notifications"
    fun pluginNotifications(): NotificationGroup {
        return NotificationGroupManager.getInstance().getNotificationGroup(GROUP_ID)
    }
}

fun NotificationGroup.showNotification(content: String, type: NotificationType, project: Project? = null) {
    createNotification(content, type).notify(project)
}

fun NotificationGroup.showNotification(
    title: String,
    content: String,
    type: NotificationType,
    project: Project? = null,
    additionalConfiguration: (Notification.() -> Notification) = { this },
) {
    createNotification(title, content, type)
        .additionalConfiguration()
        .notify(project)
}

