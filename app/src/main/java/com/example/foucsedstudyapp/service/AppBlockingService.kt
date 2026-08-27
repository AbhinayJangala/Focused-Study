package com.example.foucsedstudyapp.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.foucsedstudyapp.MainActivity

class AppBlockingService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val packageName = event?.packageName

        if (packageName == "com.example.foucsedstudyapp") {
            return
        }

        Log.d("AppBlockingService", "Current app: $packageName")

        if (packageName == "in.amazon.mShop.android.shopping") {
            Log.d("AppBlockingService", "AMAZON DETECTED - SHOULD BLOCK")

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("OPEN_TIMER", true)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        }
    }

    override fun onInterrupt() {
    }
}