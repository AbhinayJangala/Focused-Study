package com.example.foucsedstudyapp.repository

import com.example.foucsedstudyapp.data.User
import com.example.foucsedstudyapp.service.LeaderboardService

class LeaderBoardRepository
{
    private val service = LeaderboardService()

    fun getWeeklyLeaderboard(
        onSuccess: (List<User>) -> Unit,
        onFailure: (String) -> Unit
    )
    {
        service.getWeeklyLeaderboard(
            onSuccess = { users ->
                onSuccess(users)
            },
            onFailure = { error ->
                onFailure(error)
            }
        )
    }
}
