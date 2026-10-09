package com.example.foucsedstudyapp.ui.screens.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foucsedstudyapp.data.User
import com.example.foucsedstudyapp.navigation.Screen
import com.example.foucsedstudyapp.viewmodel.LeaderBoardViewModel

@Composable
fun LeaderboardScreen(
    navController: NavController,
    viewModel: LeaderBoardViewModel = viewModel()
) {
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.getWeeklyLeaderboard(
            onSuccess = { result ->
                users = result
                isLoading = false
                errorMessage = null
            },
            onFailure = { error ->
                errorMessage = error
                isLoading = false
            }
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.QueryStats, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.QueryStats, contentDescription = "Stats") },
                    label = { Text("Stats") },
                    selected = false,
                    onClick = {
                        navController.navigate(Screen.Stats.route) {
                            popUpTo(Screen.Home.route)
                            launchSingleTop = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Leaderboard, contentDescription = "Leaderboard") },
                    label = { Text("Leaderboard") },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                    )
                )
            }
        },
        containerColor = Color(0xFFF6F8FC)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Weekly Leaderboard 🏆",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF14264D)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "See who's focusing the most this week!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    Text(
                        text = "Unable to load leaderboard: $errorMessage",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                users.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No focus sessions recorded this week yet.\nStart focusing to appear here!",
                            color = Color.Gray
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(
                            items = users,
                            key = { _, user -> user.uid }
                        ) { index, user ->

                            LeaderboardUserCard(
                                rank = index + 1,
                                user = user
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LeaderboardUserCard(
    rank: Int,
    user: User
) {
    val isCurrentUser = user.uid ==
            com.google.firebase.auth.FirebaseAuth.getInstance()
                .currentUser?.uid

    val rankColor = when (rank) {
        1 -> Color(0xFFFFB300)
        2 -> Color(0xFF9E9E9E)
        3 -> Color(0xFFCD7F32)
        else -> Color(0xFF185ABD)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrentUser) {
                Color(0xFFE1ECFF)
            } else {
                Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(rankColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                if (rank <= 3) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Rank $rank",
                        tint = rankColor
                    )
                } else {
                    Text(
                        text = rank.toString(),
                        fontWeight = FontWeight.Bold,
                        color = rankColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (isCurrentUser) {
                        "${user.name.ifBlank { "You" }} (You)"
                    } else {
                        user.name.ifBlank { "User" }
                    },
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF14264D)
                )

                Text(
                    text = if (rank == 1) {
                        "Weekly champion"
                    } else {
                        "Rank #$rank"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Text(
                text = formatStudyTime(user.totalWeeklyStudyTime),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF185ABD)
            )
        }
    }
}

private fun formatStudyTime(minutes: Long): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60

    return when {
        hours > 0 && remainingMinutes > 0 ->
            "${hours}h ${remainingMinutes}m"

        hours > 0 ->
            "${hours}h"

        else ->
            "${remainingMinutes}m"
    }
}
