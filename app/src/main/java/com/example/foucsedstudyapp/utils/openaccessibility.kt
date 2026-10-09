package com.example.foucsedstudyapp.utils
import android.content.Context
import android.content.Intent
import android.provider.Settings

fun openAccessibilitySettings(context: Context) {
    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    context.startActivity(intent)
}