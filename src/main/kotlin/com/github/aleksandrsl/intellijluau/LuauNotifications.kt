package com.github.aleksandrsl.intellijluau

import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager

object LuauNotifications {
    fun pluginNotifications(): NotificationGroup {
        return NotificationGroupManager.getInstance().getNotificationGroup("Luau notifications")
    }
}
