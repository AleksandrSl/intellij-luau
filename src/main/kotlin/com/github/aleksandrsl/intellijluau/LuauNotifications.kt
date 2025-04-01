package com.github.aleksandrsl.intellijluau

import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager

object LuauNotifications {
    const val GROUP_ID = "Luau notifications"
    fun pluginNotifications(): NotificationGroup {
        return NotificationGroupManager.getInstance().getNotificationGroup(GROUP_ID)
    }
}
