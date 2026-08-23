package com.example.foucsedstudyapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foucsedstudyapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val primaryBlue = Color(0xFF0052CC)
    val primaryGreen = Color(0xFF2EBD6B)
    val backgroundColor = Color(0xFFF8FAFC)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryBlue,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color(0xFFE6F0FF)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.QueryStats, contentDescription = "Stats") },
                    label = { Text("Stats") },
                    selected = false,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Leaderboard, contentDescription = "Leaderboard") },
                    label = { Text("Leaderboard") },
                    selected = false,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray
                    )
                )
            }
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "☀️ ${uiState.greeting},",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                    Text(
                        text = uiState.userName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B3E)
                    )
                }
                
                // Streak Badge
                Surface(
                    color = Color(0xFFFFF4E5),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🔥", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${uiState.user?.streak ?: 0} Day Streak",
                            color = Color(0xFFE67E22),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Text(
                text = "Keep your streak alive!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Today's Focus Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = primaryBlue)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Today's Focus",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.studyTimeToday,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            navController.navigate("focus")

                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Start Focus Session",
                            color = primaryBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Quick Stats
            Text(
                text = "Quick Stats",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D1B3E)
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatItem(Modifier.weight(1f), "📚 Sessions", "${uiState.sessionsToday}", primaryBlue)
                StatItem(Modifier.weight(1f), "⏱ Time", uiState.studyTimeToday, primaryGreen)
                StatItem(Modifier.weight(1f), "🏆 Rank", uiState.rank, Color(0xFF9133FF))
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Leaderboard Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Leaderboard",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
                TextButton(onClick = { }) {
                    Text(text = "See All →", color = primaryBlue)
                }
            }
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    uiState.leaderboard.forEachIndexed { index, user ->
                        LeaderboardItem(
                            rank = user.rank,
                            name = user.name,
                            time = user.time,
                            isUser = user.isUser,
                            accentColor = if (user.isUser) primaryBlue else Color.Transparent
                        )
                        if (index < uiState.leaderboard.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF1F5F9))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatItem(modifier: Modifier, title: String, value: String, accentColor: Color) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = accentColor)
        }
    }
}

@Composable
fun LeaderboardItem(rank: Int, name: String, time: String, isUser: Boolean, accentColor: Color = Color.Transparent) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$rank.",
            modifier = Modifier.width(28.dp),
            fontWeight = FontWeight.Bold,
            color = if (isUser) (if (accentColor != Color.Transparent) accentColor else Color.Gray) else Color.Gray
        )
        
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (isUser) (if (accentColor != Color.Transparent) accentColor.copy(alpha = 0.1f) else Color(0xFFF1F5F9)) else Color(0xFFF1F5F9)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (isUser) (if (accentColor != Color.Transparent) accentColor else Color.Gray) else Color.Gray
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            fontWeight = if (isUser) FontWeight.Bold else FontWeight.Medium,
            color = if (isUser) (if (accentColor != Color.Transparent) accentColor else Color(0xFF0D1B3E)) else Color(0xFF0D1B3E)
        )
        
        Text(
            text = time,
            fontWeight = FontWeight.Bold,
            color = if (isUser) (if (accentColor != Color.Transparent) accentColor else Color(0xFF0D1B3E)) else Color(0xFF0D1B3E)
        )
    }
}
