package com.example.foucsedstudyapp.data

data class focusSession(
    val userId: String = "",

    val durationMinutes: Int = 0,

    val startTime: Long = 0L,

    val endTime: Long = 0L,

    val completed: Boolean = false
)

