package com.example.foucsedstudyapp

import com.example.foucsedstudyapp.navigation.AppNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.foucsedstudyapp.ui.theme.FoucsedStudyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val openTimer = intent.getBooleanExtra("OPEN_TIMER", false)
        enableEdgeToEdge()
        setContent {
            FoucsedStudyAppTheme {
                AppNavigation(openTimer = openTimer)
            }
        }
    }
}
