package com.example.foucsedstudyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foucsedstudyapp.ui.screens.home.HomeScreen
import com.example.foucsedstudyapp.ui.screens.login.LoginScreen
import com.example.foucsedstudyapp.ui.screens.WelcomeScreen
import com.example.foucsedstudyapp.ui.screens.login.SignupScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

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
    }
}
