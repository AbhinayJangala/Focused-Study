package com.example.foucsedstudyapp.service

import com.example.foucsedstudyapp.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeService {
    val auth = FirebaseAuth.getInstance();
    val firestore = FirebaseFirestore.getInstance();
    val currentUser = auth.currentUser;
    val userId = currentUser?.uid;
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

        val userRef = firestore
            .collection("users")
            .document(currentUserId)

        val today = java.util.Calendar.getInstance()

        // Set time to the beginning of today
        today.set(java.util.Calendar.HOUR_OF_DAY, 0)
        today.set(java.util.Calendar.MINUTE, 0)
        today.set(java.util.Calendar.SECOND, 0)
        today.set(java.util.Calendar.MILLISECOND, 0)

        val todayMillis = today.timeInMillis

        firestore.runTransaction { transaction ->

            val snapshot = transaction.get(userRef)

            val currentStreak = snapshot.getLong("streak") ?: 0L
            val currentSessionsToday = snapshot.getLong("sessionsToday") ?: 0L
            val currentStudyTimeToday = snapshot.getLong("studyTimeToday") ?: 0L
            val lastStudyDate = snapshot.getLong("lastStudyDate") ?: 0L

            val yesterdayMillis = todayMillis - 24 * 60 * 60 * 1000L

            val newStreak: Long
            val newSessionsToday: Long
            val newStudyTimeToday: Long

            if (lastStudyDate == todayMillis) {

                // Already studied today
                newStreak = currentStreak
                newSessionsToday = currentSessionsToday + 1
                newStudyTimeToday = currentStudyTimeToday + studyTimeMinutes

            } else if (lastStudyDate == yesterdayMillis) {

                // Studied yesterday → continue streak
                newStreak = currentStreak + 1
                newSessionsToday = 1
                newStudyTimeToday = studyTimeMinutes

            } else {

                // First study session or streak was broken
                newStreak = 1
                newSessionsToday = 1
                newStudyTimeToday = studyTimeMinutes
            }

            transaction.update(
                userRef,
                mapOf(
                    "streak" to newStreak,
                    "sessionsToday" to newSessionsToday,
                    "studyTimeToday" to newStudyTimeToday,
                    "lastStudyDate" to todayMillis
                )
            )

        }.addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            onFailure(exception.message ?: "Failed to update study statistics")
        }
    }


}