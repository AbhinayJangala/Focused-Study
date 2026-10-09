package com.example.foucsedstudyapp.service

import com.google.firebase.auth.FirebaseAuth
import com.example.foucsedstudyapp.data.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.GoogleAuthProvider

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

    fun signInWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                val firebaseUser = authResult.user
                val uid = firebaseUser?.uid
                if (uid == null) {
                    onSuccess()
                    return@addOnSuccessListener
                }
                val userRef = firestore.collection("users").document(uid)
                userRef.get().addOnSuccessListener { document ->
                    if (!document.exists()) {
                        val name = firebaseUser.displayName ?: "User"
                        val email = firebaseUser.email ?: ""
                        val user = User(uid = uid, name = name, email = email)
                        userRef.set(user)
                            .addOnSuccessListener { onSuccess() }
                            .addOnFailureListener { onSuccess() }
                    } else {
                        onSuccess()
                    }
                }.addOnFailureListener {
                    onSuccess()
                }
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Google Sign-In failed")
            }
    }
    fun resetPassword(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(
                    exception.message ?: "Failed to send password reset email"
                )
            }
    }

    fun signOut() {
        auth.signOut()
    }
}
