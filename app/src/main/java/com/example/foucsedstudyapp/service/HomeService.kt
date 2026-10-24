package com.example.foucsedstudyapp.service

import com.example.foucsedstudyapp.data.User
import com.example.foucsedstudyapp.utils.DateUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeService {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun getUser(
        onSuccess: (User) -> Unit,
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

        userRef.get().addOnSuccessListener { document ->
            val user = document.toObject(User::class.java)

            if (user != null) {
                val now = System.currentTimeMillis()
                val isToday = DateUtils.isSameDay(user.lastStudyDate, now)
                val isYesterday = DateUtils.isYesterday(user.lastStudyDate, now)

                // If user studied today or yesterday, streak is preserved.
                // If the user missed yesterday and hasn't studied today, streak resets to 0.
                val effectiveStreak = if (isToday || isYesterday) user.streak else 0

                // Daily metrics are reset if no study session has occurred today
                val effectiveSessionsToday = if (isToday) user.sessionsToday else 0
                val effectiveStudyTimeToday = if (isToday) user.studyTimeToday else 0L

                // Weekly metrics are reset if a new week has started
                val currentWeekStart = DateUtils.getStartOfWeek(now)
                val isCurrentWeek = user.weekStartDate == currentWeekStart
                val effectiveWeeklyTime = if (isCurrentWeek) user.totalWeeklyStudyTime else 0L
                val effectiveWeekStart = if (isCurrentWeek) user.weekStartDate else currentWeekStart

                val needsUpdate = effectiveStreak != user.streak ||
                        effectiveSessionsToday != user.sessionsToday ||
                        effectiveStudyTimeToday != user.studyTimeToday ||
                        effectiveWeeklyTime != user.totalWeeklyStudyTime ||
                        effectiveWeekStart != user.weekStartDate

                val sanitizedUser = user.copy(
                    streak = effectiveStreak,
                    sessionsToday = effectiveSessionsToday,
                    studyTimeToday = effectiveStudyTimeToday,
                    totalWeeklyStudyTime = effectiveWeeklyTime,
                    weekStartDate = effectiveWeekStart
                )

                if (needsUpdate) {
                    val updates = mapOf<String, Any>(
                        "streak" to effectiveStreak,
                        "sessionsToday" to effectiveSessionsToday,
                        "studyTimeToday" to effectiveStudyTimeToday,
                        "totalWeeklyStudyTime" to effectiveWeeklyTime,
                        "weekStartDate" to effectiveWeekStart
                    )
                    userRef.update(updates)
                }

                onSuccess(sanitizedUser)
            } else {
                onFailure("User not found")
            }
        }.addOnFailureListener { exception ->
            onFailure(exception.message ?: "Unknown error")
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

        val now = System.currentTimeMillis()
        val todayStartMillis = DateUtils.getStartOfDay(now)
        val weekStartMillis = DateUtils.getStartOfWeek(now)

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

            val isToday = DateUtils.isSameDay(lastStudyDate, now)
            val isYesterday = DateUtils.isYesterday(lastStudyDate, now)

            val newStreak: Long
            val newSessionsToday: Long
            val newStudyTimeToday: Long

            when {
                isToday -> {
                    // Already studied today: keep streak (at least 1), increment today's stats
                    newStreak = maxOf(1L, currentStreak)
                    newSessionsToday = currentSessionsToday + 1
                    newStudyTimeToday = currentStudyTimeToday + studyTimeMinutes
                }

                isYesterday -> {
                    // Studied yesterday: increment streak by 1, start fresh today's stats
                    newStreak = currentStreak + 1
                    newSessionsToday = 1L
                    newStudyTimeToday = studyTimeMinutes
                }

                else -> {
                    // Missed previous day(s) or new user: streak restarts at 1
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
                    "lastStudyDate" to todayStartMillis,
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
