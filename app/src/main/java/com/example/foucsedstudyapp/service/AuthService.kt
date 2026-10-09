package com.example.foucsedstudyapp.service

import com.google.firebase.auth.FirebaseAuth
import com.example.foucsedstudyapp.data.User
import com.google.firebase.firestore.FirebaseFirestore
class AuthService {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun signUp(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid =auth.currentUser?.uid
                if (uid == null) {
                    return@addOnSuccessListener
                }
                val user = User(
                    uid = uid,
                    name = name,
                    email = email
                )
                firestore.collection("users")
                    .document(uid)
                    .set(user)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onFailure(it.message ?: "Failed to save user")
                    }
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Unknown Error")
            }
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Invalid Email or Password")
            }
    }

    fun signOut() {
        auth.signOut()
    }
}
