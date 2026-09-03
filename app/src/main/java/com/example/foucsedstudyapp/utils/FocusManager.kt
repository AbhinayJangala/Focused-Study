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
            apply()
        }
    }

    fun stopFocus() {
        prefs.edit().apply {
            putBoolean("is_focus_active", false)
            putLong("focus_end_time", 0)
            putStringSet("blocked_apps", emptySet())
            apply()
        }
    }

    fun isFocusActive(): Boolean {
        val active = prefs.getBoolean("is_focus_active", false)
        val endTime = prefs.getLong("focus_end_time", 0)
        
        if (active && System.currentTimeMillis() > endTime) {
            stopFocus()
            return false
        }
        return active
    }

    fun getBlockedApps(): Set<String> {
        return prefs.getStringSet("blocked_apps", emptySet()) ?: emptySet()
    }

    fun getRemainingSeconds(): Long {
        val endTime = prefs.getLong("focus_end_time", 0)
        val remainingMillis = endTime - System.currentTimeMillis()
        return if (remainingMillis > 0) remainingMillis / 1000 else 0
    }

    fun getSessionDurationSeconds(): Long {
        return prefs.getLong("focus_duration_seconds", 0)
    }
}