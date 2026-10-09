package com.example.foucsedstudyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foucsedstudyapp.ui.screens.home.HomeScreen
import com.example.foucsedstudyapp.ui.screens.login.LoginScreen
import com.example.foucsedstudyapp.ui.screens.WelcomeScreen
import com.example.foucsedstudyapp.ui.screens.focus.FocusScreen
import com.example.foucsedstudyapp.ui.screens.focus.TimerScreen
import com.example.foucsedstudyapp.ui.screens.login.SignupScreen
import com.example.foucsedstudyapp.ui.screens.stats.StatsScreen
import com.example.foucsedstudyapp.ui.screens.permissions.AccessibilityPermissionScreen
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.foucsedstudyapp.ui.screens.leaderboard.LeaderboardScreen
import com.example.foucsedstudyapp.utils.FocusManager
import com.example.foucsedstudyapp.utils.isAccessibilityServiceEnabled
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(
    openTimer: Boolean = false,
    initialSeconds: Long = -1L,
    totalDuration: Long = -1L,
    onRedirectHandled: () -> Unit = {}
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val focusManager = remember { FocusManager(context) }

    val isFocusActive = focusManager.isFocusActive()
    val remainingSeconds = focusManager.getRemainingSeconds()
    val sessionTotal = focusManager.getSessionDurationSeconds()

    LaunchedEffect(openTimer, initialSeconds, totalDuration) {
        if (openTimer) {
            onRedirectHandled()
            val route = if (initialSeconds > 0) {
                "timer/$initialSeconds?total=$totalDuration"
            } else {
                "timer/${30 * 60}?total=${30 * 60}"
            }
            navController.navigate(route) {
                // Ensure we don't build up multiple timer screens
                launchSingleTop = true
            }
        }
    }
    val startDestination = when {
        isFocusActive && remainingSeconds > 0 -> {
            "timer/$remainingSeconds?total=$sessionTotal"
        }
        FirebaseAuth.getInstance().currentUser != null -> {
            if (isAccessibilityServiceEnabled(context)) {
                Screen.Home.route
            } else {
                Screen.AccessibilityPermission.route
            }
        }
        else -> {
            Screen.Welcome.route
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.SignUp.route) {
            SignupScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.AccessibilityPermission.route) {
            AccessibilityPermissionScreen(navController)
        }

        composable(Screen.Focus.route) {
            FocusScreen(navController)
        }
        composable(Screen.Leaderboard.route) {
            LeaderboardScreen(navController)
        }
        composable(Screen.Stats.route) {
            StatsScreen(navController)
        }
        composable(Screen.Timer.route) { backStackEntry ->

            val time = backStackEntry.arguments?.getString("time")?.toLong() ?: (30 * 60L)
            val total = backStackEntry.arguments?.getString("total")?.toLong() ?: time

            TimerScreen(
                navController = navController,
                initialSeconds = time,
                totalDuration = total
            )
        }
    }
}
