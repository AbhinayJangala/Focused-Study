package com.example.foucsedstudyapp.viewmodel

import com.example.foucsedstudyapp.data.User
import com.example.foucsedstudyapp.data.focusSession

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val time: String,
    val isUser: Boolean = false
)

data class HomeUiState(
    val user: User? = null,
    val userName: String = "User",
    val greeting: String = "Good Morning",
    val studyTimeToday: String = "0m",
    val streak: Int = 0,
    val sessionsToday: Int = 0,
    val rank: String = "#--",
    val leaderboard: List<LeaderboardUser> = emptyList(),
    val focusSessions: List<focusSession> = emptyList()
)
