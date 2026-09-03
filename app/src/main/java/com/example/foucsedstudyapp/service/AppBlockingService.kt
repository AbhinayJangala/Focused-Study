package com.example.foucsedstudyapp.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.foucsedstudyapp.MainActivity
import com.example.foucsedstudyapp.utils.FocusManager

class AppBlockingService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val packageName = event?.packageName?.toString() ?: return
        val focusManager = FocusManager(this)

        if (packageName == "com.example.foucsedstudyapp") {
            return
        }

        if (focusManager.isFocusActive()) {
            val blockedApps = focusManager.getBlockedApps()

            if (packageName in blockedApps) {
                Log.d("AppBlockingService", "BLOCKED APP DETECTED: $packageName")

                val remainingSeconds = focusManager.getRemainingSeconds()
                val totalDuration = focusManager.getSessionDurationSeconds()
                
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("OPEN_TIMER", true)
                    putExtra("REMAINING_SECONDS", remainingSeconds)
                    putExtra("TOTAL_DURATION", totalDuration)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }

                startActivity(intent)
            }
        }
    }

    override fun onInterrupt() {
    }
}