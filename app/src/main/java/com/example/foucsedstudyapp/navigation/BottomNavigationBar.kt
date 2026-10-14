package com.example.foucsedstudyapp.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

enum class NavigationTab {
    HOME, STATS, LEADERBOARD
}

@Composable
fun AppBottomBar(
    currentTab: NavigationTab,
    navController: NavController
) {
    NavigationBar(
        modifier = Modifier
            .shadow(16.dp, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = if (currentTab == NavigationTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home", fontSize = 12.sp) },
            selected = currentTab == NavigationTab.HOME,
            onClick = {
                if (currentTab != NavigationTab.HOME) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = if (currentTab == NavigationTab.STATS) Icons.Filled.QueryStats else Icons.Outlined.QueryStats,
                    contentDescription = "Stats"
                )
            },
            label = { Text("Stats", fontSize = 12.sp) },
            selected = currentTab == NavigationTab.STATS,
            onClick = {
                if (currentTab != NavigationTab.STATS) {
                    navController.navigate(Screen.Stats.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = if (currentTab == NavigationTab.LEADERBOARD) Icons.Filled.Leaderboard else Icons.Outlined.Leaderboard,
                    contentDescription = "Leaderboard"
                )
            },
            label = { Text("Leaderboard", fontSize = 12.sp) },
            selected = currentTab == NavigationTab.LEADERBOARD,
            onClick = {
                if (currentTab != NavigationTab.LEADERBOARD) {
                    navController.navigate(Screen.Leaderboard.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
            )
        )
    }
}
