package com.example.foucsedstudyapp.service

import com.example.foucsedstudyapp.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeService {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser
    val userId = currentUser?.uid
    fun getUser(
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) {

        if (userId != null) {

            val userRef = firestore
                .collection("users")
                .document(userId!!)

            userRef.get().addOnSuccessListener { document ->

                val user = document.toObject(User::class.java)

                if (user != null) {
                    onSuccess(user)
                }
            }
                .addOnFailureListener { exception ->
                    onFailure(exception.message ?: "Unknown error")
                }
        } else {
            onFailure("User not authenticated")
        }
    }
    fun updateStudyStats(
        studyTimeMinutes: Long,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val currentUserId = auth.currentUser?.uid

        if (currentUserId == null) {
            onFailure("User not authenticated")
            return
        }

        if (studyTimeMinutes <= 0L) {
            onFailure("Study time must be greater than zero")
            return
        }

        val userRef = firestore
            .collection("users")
            .document(currentUserId)

        val calendar = java.util.Calendar.getInstance()

        // Start of today
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)

        val todayMillis = calendar.timeInMillis

        // Start of this week: Monday at midnight
        val weekCalendar = calendar.clone() as java.util.Calendar

        val dayOfWeek = weekCalendar.get(java.util.Calendar.DAY_OF_WEEK)

        val daysSinceMonday =
            (dayOfWeek - java.util.Calendar.MONDAY + 7) % 7

        weekCalendar.add(
            java.util.Calendar.DAY_OF_MONTH,
            -daysSinceMonday
        )

        val weekStartMillis = weekCalendar.timeInMillis

        firestore.runTransaction { transaction ->

            val snapshot = transaction.get(userRef)

            if (!snapshot.exists()) {
                throw IllegalStateException("User document not found")
            }

            val currentStreak = snapshot.getLong("streak") ?: 0L
            val currentSessionsToday =
                snapshot.getLong("sessionsToday") ?: 0L
            val currentStudyTimeToday =
                snapshot.getLong("studyTimeToday") ?: 0L
            val lastStudyDate =
                snapshot.getLong("lastStudyDate") ?: 0L

            val storedWeekStart =
                snapshot.getLong("weekStartDate") ?: 0L
            val storedWeeklyTime =
                snapshot.getLong("totalWeeklyStudyTime") ?: 0L

            val yesterdayCalendar =
                calendar.clone() as java.util.Calendar

            yesterdayCalendar.add(
                java.util.Calendar.DAY_OF_MONTH,
                -1
            )

            val yesterdayMillis = yesterdayCalendar.timeInMillis

            val newStreak: Long
            val newSessionsToday: Long
            val newStudyTimeToday: Long

            when (lastStudyDate) {
                todayMillis -> {
                    newStreak = currentStreak
                    newSessionsToday = currentSessionsToday + 1
                    newStudyTimeToday =
                        currentStudyTimeToday + studyTimeMinutes
                }

                yesterdayMillis -> {
                    newStreak = currentStreak + 1
                    newSessionsToday = 1L
                    newStudyTimeToday = studyTimeMinutes
                }

                else -> {
                    newStreak = 1L
                    newSessionsToday = 1L
                    newStudyTimeToday = studyTimeMinutes
                }
            }

            // Reset weekly total when the week changes
            val newWeeklyTime =
                if (storedWeekStart == weekStartMillis) {
                    storedWeeklyTime + studyTimeMinutes
                } else {
                    studyTimeMinutes
                }

            transaction.update(
                userRef,
                mapOf(
                    "streak" to newStreak,
                    "sessionsToday" to newSessionsToday,
                    "studyTimeToday" to newStudyTimeToday,
                    "lastStudyDate" to todayMillis,
                    "totalWeeklyStudyTime" to newWeeklyTime,
                    "weekStartDate" to weekStartMillis
                )
            )

            null
        }.addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            onFailure(
                exception.message ?: "Failed to update study statistics"
            )
        }
    }

}