package com.example.foucsedstudyapp.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.example.foucsedstudyapp.data.focusSession

class FocusRepository {
    private val firestore = FirebaseFirestore.getInstance();

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

}