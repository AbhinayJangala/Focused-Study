package com.example.foucsedstudyapp.viewmodel

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.foucsedstudyapp.repository.AuthRepository

class SignUpViewModel : ViewModel() {

    private val repository = AuthRepository()

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")
    val signUpSuccess = mutableStateOf(false)

    fun signUp(name: String, email: String, password: String, confirmPass: String) {
        if (name.isEmpty()) {
            errorMessage.value = "Name cannot be empty"
            return
        }
        if (email.isEmpty()) {
            errorMessage.value = "Email cannot be empty"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage.value = "Invalid email format"
            return
        }
        if (password.length < 6) {
            errorMessage.value = "Password must be at least 6 characters"
            return
        }
        if (password != confirmPass) {
            errorMessage.value = "Passwords do not match"
            return
        }

        isLoading.value = true
        errorMessage.value = ""

        repository.signUp(
            email = email,
            password = password,
            onSuccess = {
                isLoading.value = false
                signUpSuccess.value = true
            },
            onFailure = { error ->
                isLoading.value = false
                errorMessage.value = error
            }
        )
    }
}
