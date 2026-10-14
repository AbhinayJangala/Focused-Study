package com.example.foucsedstudyapp

import com.example.foucsedstudyapp.navigation.AppNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.foucsedstudyapp.ui.theme.FoucsedStudyAppTheme

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import com.example.foucsedstudyapp.utils.NoInternetScreen
import com.example.foucsedstudyapp.utils.isInternetAvailable
import com.example.foucsedstudyapp.utils.rememberNetworkConnectivity
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    private var openTimer by mutableStateOf(false)
    private var remainingSeconds by mutableStateOf(-1L)
    private var totalDuration by mutableStateOf(-1L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
        
        enableEdgeToEdge()
        setContent {
            FoucsedStudyAppTheme {
                val isNetworkConnected = rememberNetworkConnectivity()
                val context = LocalContext.current

                if (!isNetworkConnected.value) {
                    NoInternetScreen(
                        onRetry = {
                            isNetworkConnected.value = isInternetAvailable(context)
                        }
                    )
                } else {
                    AppNavigation(
                        openTimer = openTimer,
                        initialSeconds = remainingSeconds,
                        totalDuration = totalDuration,
                        onRedirectHandled = { 
                            openTimer = false 
                        }
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        openTimer = intent?.getBooleanExtra("OPEN_TIMER", false) ?: false
        remainingSeconds = intent?.getLongExtra("REMAINING_SECONDS", -1L) ?: -1L
        totalDuration = intent?.getLongExtra("TOTAL_DURATION", -1L) ?: -1L
    }
}
