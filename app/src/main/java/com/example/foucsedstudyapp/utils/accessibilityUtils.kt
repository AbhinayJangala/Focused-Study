package com.example.foucsedstudyapp.utils

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import com.example.foucsedstudyapp.service.AppBlockingService

fun isAccessibilityServiceEnabled(context: Context): Boolean {
    val expectedComponent = ComponentName(
        context,
        AppBlockingService::class.java
    )

    val enabledServices = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    ) ?: return false

    val colonSplitter = TextUtils.SimpleStringSplitter(':')
    colonSplitter.setString(enabledServices)

    while (colonSplitter.hasNext()) {
        val componentName = ComponentName.unflattenFromString(
            colonSplitter.next()
        )

        if (componentName == expectedComponent) {
            return true
        }
    }

    return false
}