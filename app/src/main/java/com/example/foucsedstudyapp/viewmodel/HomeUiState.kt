package com.example.foucsedstudyapp.viewmodel

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val time: String,
    val isUser: Boolean = false
)

data class HomeUiState(
    val userName: String = "Abhinay",
    val greeting: String = "Good Morning",
    val studyTimeToday: String = "2h 15m",
    val streak: Int = 5,
    val sessionsToday: Int = 4,
    val rank: String = "#18",
    val leaderboard: List<LeaderboardUser> = listOf(
        LeaderboardUser(1, "Rahul", "8h"),
        LeaderboardUser(2, "Priya", "7h"),
        LeaderboardUser(3, "You", "6h", true)
    )
)
