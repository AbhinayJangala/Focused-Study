package com.example.foucsedstudyapp.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object Focus : Screen("focus")
    object Timer : Screen("timer/{time}?total={total}")

}