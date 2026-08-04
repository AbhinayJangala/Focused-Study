package com.example.foucsedstudyapp.viewmodel

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.foucsedstudyapp.repository.AuthRepository

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")
    val loginSuccess = mutableStateOf(false)

    fun login(email: String, password: String) {
        if (email.isEmpty()) {
            errorMessage.value = "Email cannot be empty"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage.value = "Invalid email format"
            return
        }
        if (password.isEmpty()) {
            errorMessage.value = "Password cannot be empty"
            return
        }

        isLoading.value = true
        errorMessage.value = ""

        repository.login(
            email = email,
            password = password,
            onSuccess = {
                isLoading.value = false
                loginSuccess.value = true
            },
            onFailure = { error ->
                isLoading.value = false
                errorMessage.value = error
            }
        )
    }
}
