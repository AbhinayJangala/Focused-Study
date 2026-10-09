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
        if (password.length <= 8) {
            errorMessage.value = "Password must be more than 8 characters"
            return
        }
        if (!password.any { it.isUpperCase() }) {
            errorMessage.value = "Password must contain at least one uppercase letter"
            return
        }
        if (!password.any { it.isLowerCase() }) {
            errorMessage.value = "Password must contain at least one lowercase letter"
            return
        }
        if (!password.any { it.isDigit() }) {
            errorMessage.value = "Password must contain at least one number"
            return
        }
        if (!password.any { !it.isLetterOrDigit() }) {
            errorMessage.value = "Password must contain at least one special character"
            return
        }
        if (password != confirmPass) {
            errorMessage.value = "Passwords do not match"
            return
        }

        isLoading.value = true
        errorMessage.value = ""

        repository.signUp(
            name = name,
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
