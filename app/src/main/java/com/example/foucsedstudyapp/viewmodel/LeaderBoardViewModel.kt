package com.example.foucsedstudyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foucsedstudyapp.data.User
import com.example.foucsedstudyapp.repository.LeaderBoardRepository

class LeaderBoardViewModel : ViewModel() {

    private val repository = LeaderBoardRepository()

    fun getWeeklyLeaderboard(
        onSuccess: (List<User>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        repository.getWeeklyLeaderboard(
            onSuccess = { users ->
                onSuccess(users)
            },
            onFailure = { error ->
                onFailure(error)
            }
        )
    }
}