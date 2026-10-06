package com.example.foucsedstudyapp.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.example.foucsedstudyapp.data.focusSession

class FocusRepository {
    private val firestore = FirebaseFirestore.getInstance();

        fun saveFocusSession(session: focusSession,
                             onSuccesss: () -> Unit,
                             onErroe: (String) -> Unit
        ) {
            firestore.collection("focusSessions").add(session)
                .addOnSuccessListener {
                    onSuccesss()
                }
                .addOnFailureListener { exception ->
                    onErroe(exception.message ?: "Unknown error occurred")

                }
        }

}