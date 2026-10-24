package com.example.foucsedstudyapp.service

import com.example.foucsedstudyapp.data.User
import com.example.foucsedstudyapp.utils.DateUtils
import com.google.firebase.firestore.FirebaseFirestore

class LeaderboardService {

    private val firestore = FirebaseFirestore.getInstance()

    fun getWeeklyLeaderboard(
        onSuccess: (List<User>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val now = System.currentTimeMillis()
        val weekStartMillis = DateUtils.getStartOfWeek(now)

        firestore.collection("users")
            .whereEqualTo("weekStartDate", weekStartMillis)
            .get()
            .addOnSuccessListener { result ->
                val users = result.documents.mapNotNull { document ->
                    val user = document.toObject(User::class.java) ?: return@mapNotNull null
                    val effectiveStreak = DateUtils.calculateEffectiveStreak(
                        streak = user.streak,
                        lastStudyDate = user.lastStudyDate,
                        currentMillis = now
                    )
                    user.copy(streak = effectiveStreak)
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
