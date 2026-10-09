package com.example.foucsedstudyapp.repository

import com.example.foucsedstudyapp.data.User
import com.example.foucsedstudyapp.service.HomeService

class HomeRepository {

    private val service = HomeService()

    fun getUser(
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) {
        service.getUser(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
    fun updateStudyStats(
        studyTimeMinutes: Long,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        service.updateStudyStats(
            studyTimeMinutes = studyTimeMinutes,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}