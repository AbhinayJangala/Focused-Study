package com.example.foucsedstudyapp.service

import com.example.foucsedstudyapp.data.User
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class LeaderboardService {

    private val firestore = FirebaseFirestore.getInstance()

    fun getWeeklyLeaderboard(
        onSuccess: (List<User>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val calendar = Calendar.getInstance()

        // Start of today
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // Calculate Monday at midnight
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val daysSinceMonday =
            (dayOfWeek - Calendar.MONDAY + 7) % 7

        calendar.add(Calendar.DAY_OF_MONTH, -daysSinceMonday)

        val weekStartMillis = calendar.timeInMillis

        firestore.collection("users")
            .whereEqualTo("weekStartDate", weekStartMillis)
            .get()
            .addOnSuccessListener { result ->

                val users = result.documents.mapNotNull { document ->
                    document.toObject(User::class.java)
                }
                    .sortedByDescending { it.totalWeeklyStudyTime }

                onSuccess(users)
            }
            .addOnFailureListener { exception ->
                onFailure(
                    exception.message ?: "Failed to load leaderboard"
                )
            }
    }
}