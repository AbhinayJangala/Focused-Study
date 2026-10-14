package com.example.foucsedstudyapp.utils

import android.content.Context
import android.content.SharedPreferences

class FocusManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("focus_prefs", Context.MODE_PRIVATE)

    fun startFocus(apps: Set<String>, minutes: Int) {
        val durationMillis = minutes * 60 * 1000L
        val endTime = System.currentTimeMillis() + durationMillis
        prefs.edit().apply {
            putStringSet("blocked_apps", apps)
            putLong("focus_end_time", endTime)
            putLong("focus_duration_seconds", (minutes * 60).toLong())
            putBoolean("is_focus_active", true)
            putBoolean("is_focus_paused", false)
            putLong("paused_remaining_seconds", 0L)
            apply()
        }
    }

    fun pauseFocus(remainingSeconds: Long) {
        prefs.edit().apply {
            putBoolean("is_focus_paused", true)
            putLong("paused_remaining_seconds", remainingSeconds)
            apply()
        }
    }

    fun resumeFocus(remainingSeconds: Long) {
        val endTime = System.currentTimeMillis() + (remainingSeconds * 1000L)
        prefs.edit().apply {
            putBoolean("is_focus_paused", false)
            putLong("focus_end_time", endTime)
            putLong("paused_remaining_seconds", 0L)
            apply()
        }
    }

    fun stopFocus() {
        prefs.edit().apply {
            putBoolean("is_focus_active", false)
            putBoolean("is_focus_paused", false)
            putLong("focus_end_time", 0)
            putLong("paused_remaining_seconds", 0L)
            putStringSet("blocked_apps", emptySet())
            apply()
        }
    }

    fun isFocusPaused(): Boolean {
        return prefs.getBoolean("is_focus_paused", false)
    }

    fun isFocusActive(): Boolean {
        val active = prefs.getBoolean("is_focus_active", false)
        val paused = isFocusPaused()
        val endTime = prefs.getLong("focus_end_time", 0)
        
        if (active && !paused && System.currentTimeMillis() > endTime) {
            stopFocus()
            return false
        }
        return active
    }

    fun getBlockedApps(): Set<String> {
        return prefs.getStringSet("blocked_apps", emptySet()) ?: emptySet()
    }

    fun getRemainingSeconds(): Long {
        if (isFocusPaused()) {
            return prefs.getLong("paused_remaining_seconds", 0L)
        }
        val endTime = prefs.getLong("focus_end_time", 0)
        val remainingMillis = endTime - System.currentTimeMillis()
        return if (remainingMillis > 0) remainingMillis / 1000 else 0
    }

    fun getSessionDurationSeconds(): Long {
        return prefs.getLong("focus_duration_seconds", 0)
    }
}