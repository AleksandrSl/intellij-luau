package com.github.aleksandrsl.intellijluau.lsp

import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager

private const val GROUP_ID = "Luau sourcemap generation"

interface SourcemapGenerator {
    /**
     * Provide to show in the notifications
     */
    val name: String

    /**
     * Starts the sourcemap generation.
     * Idempotent: calling multiple times if already started should have no effect.
     */
    fun start()

    /**
     * Stops the sourcemap generation.
     * Idempotent: calling multiple times if already stopped should have no effect.
     */
    suspend fun stop()

    fun isRunning(): Boolean

    companion object {
        internal fun notifications(): NotificationGroup {
            return NotificationGroupManager.getInstance().getNotificationGroup(GROUP_ID)
        }
    }
}
