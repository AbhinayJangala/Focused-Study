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
fun AppNavigation(openTimer: Boolean = false) {
    val navController = rememberNavController()

    LaunchedEffect(openTimer) {
        if (openTimer) {
            navController.navigate("timer/30")
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

            val minutes = backStackEntry.arguments?.getString("minutes")?.toInt() ?: 30

            TimerScreen(
                navController = navController,
                initialMinutes = minutes
            )
        }
    }
}
