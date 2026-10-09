package com.example.foucsedstudyapp.data

data class User(

    val uid: String = "",

    val name: String = "",

    val email: String = "",

    val streak: Int = 0,

    val studyTimeToday: Long = 0L,

    val sessionsToday: Int = 0,

    val lastStudyDate: Long = 0L,
    val totalWeeklyStudyTime: Long = 0L,
    val weekStartDate: Long = 0L

)