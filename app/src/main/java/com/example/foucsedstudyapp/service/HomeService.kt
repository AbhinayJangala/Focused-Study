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


}