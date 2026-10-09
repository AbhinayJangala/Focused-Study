package com.example.foucsedstudyapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.foucsedstudyapp.data.focusSession

class FocusRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun saveFocusSession(session: focusSession,
                         onSuccess: () -> Unit,
                         onFailure: (String) -> Unit
    ) {
        firestore.collection("focusSessions").add(session)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Unknown error occurred")
            }
    }

    fun getFocusSessions(
        onSuccess: (List<focusSession>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            onFailure("User not authenticated")
            return
        }
        firestore.collection("focusSessions")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val sessions = result.documents.mapNotNull { doc ->
                    doc.toObject(focusSession::class.java)
                }.sortedByDescending { it.startTime }
                onSuccess(sessions)
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Failed to load sessions")
            }
    }
}
