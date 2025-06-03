package com.github.aleksandrsl.intellijluau.lsp

import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager
import com.intellij.openapi.Disposable

private const val GROUP_ID = "Luau sourcemap generation"

interface SourcemapGenerator : Disposable {
    /**
     * Starts the sourcemap generation.
     * Idempotent: calling multiple times if already started should have no effect.
     */
    fun start()

    /**
     * Stops the sourcemap generation.
     * Idempotent: calling multiple times if already stopped should have no effect.
     */
    fun stop()

    fun isRunning(): Boolean

    companion object {
        internal fun notifications(): NotificationGroup {
            return NotificationGroupManager.getInstance().getNotificationGroup(GROUP_ID)
        }
    }
}
