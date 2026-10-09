package com.example.foucsedstudyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foucsedstudyapp.repository.AuthRepository
import com.example.foucsedstudyapp.repository.FocusRepository
import com.example.foucsedstudyapp.repository.HomeRepository
import com.example.foucsedstudyapp.repository.LeaderBoardRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val repository = HomeRepository()
    private val focusRepository = FocusRepository()
    private val leaderboardRepository = LeaderBoardRepository()
    private val authRepository = AuthRepository()

    init {
        getUser()
        loadFocusSessions()
        loadLeaderboard()
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
                    studyTimeToday = formatTime(user.studyTimeToday)
                )
            },
            onFailure = { error ->
                // Handle error
            }
        )
    }

    fun loadFocusSessions() {
        focusRepository.getFocusSessions(
            onSuccess = { sessions ->
                _uiState.value = _uiState.value.copy(focusSessions = sessions)
            },
            onFailure = { error ->
                // Handle error
            }
        )
    }

    fun loadLeaderboard() {
        leaderboardRepository.getWeeklyLeaderboard(
            onSuccess = { users ->
                val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
                val userIndex = users.indexOfFirst { it.uid == currentUserId }
                val rankStr = if (userIndex >= 0) "#${userIndex + 1}" else "#--"

                val topUsers = users.take(3).mapIndexed { index, user ->
                    LeaderboardUser(
                        rank = index + 1,
                        name = user.name.ifBlank { "User" },
                        time = formatTime(user.totalWeeklyStudyTime),
                        isUser = user.uid == currentUserId
                    )
                }

                _uiState.value = _uiState.value.copy(
                    rank = rankStr,
                    leaderboard = topUsers
                )
            },
            onFailure = { error ->
                // Handle error
            }
        )
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        authRepository.signOut()
        onLogoutSuccess()
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
