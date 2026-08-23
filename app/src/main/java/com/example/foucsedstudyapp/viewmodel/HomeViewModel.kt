package com.example.foucsedstudyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foucsedstudyapp.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val repository = HomeRepository()

    init {
        getUser()
    }

    fun getUser() {
        repository.getUser(
            onSuccess = { user ->
                _uiState.value = _uiState.value.copy(
                    user = user,
                    userName = user.name.ifEmpty { "User" },
                    greeting = getGreeting(),
                    streak = user.streak,
                    sessionsToday = user.sessionsToday,
                    studyTimeToday = formatTime(user.studyTimeToday),
                    // Adding dummy leaderboard for now, this can be fetched from a repository later
                    leaderboard = listOf(
                        LeaderboardUser(1, "Rahul", "8h"),
                        LeaderboardUser(2, "Priya", "7h"),
                        LeaderboardUser(user.streak + 10, "You", formatTime(user.studyTimeToday), true)
                    )
                )
            },
            onFailure = { error ->
                // Handle error
            }
        )
    }

    private fun getGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 0..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    private fun formatTime(minutes: Long): String {
        if (minutes == 0L) return "0m"
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return if (hours > 0) "${hours}h ${remainingMinutes}m" else "${remainingMinutes}m"
    }
}
