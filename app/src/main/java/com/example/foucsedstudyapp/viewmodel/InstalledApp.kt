package com.example.foucsedstudyapp.ui.screens.focus

import android.graphics.drawable.Drawable
import android.content.Context
import android.content.Intent

data class InstalledApp(
    val name: String,
    val packageName: String,
    val icon: Drawable
)
fun getInstalledApps(context: Context): List<InstalledApp> {
    val packageManager = context.packageManager

    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
    }

    val apps = packageManager.queryIntentActivities(intent, 0)

    return apps.map { resolveInfo ->
        InstalledApp(
            name = resolveInfo.loadLabel(packageManager).toString(),
            packageName = resolveInfo.activityInfo.packageName,
            icon = resolveInfo.loadIcon(packageManager)
        )
    }
}