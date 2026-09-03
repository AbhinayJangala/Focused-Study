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
import androidx.compose.runtime.LaunchedEffect
@Composable
fun AppNavigation(
    openTimer: Boolean = false,
    initialSeconds: Long = -1L,
    totalDuration: Long = -1L,
    onRedirectHandled: () -> Unit = {}
) {
    val navController = rememberNavController()

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
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
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


        composable(Screen.Focus.route) {
            FocusScreen(navController)
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
