package com.example.foucsedstudyapp.repository

import com.example.foucsedstudyapp.service.AuthService

class AuthRepository {

    private val authService = AuthService()

    fun signUp(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        authService.signUp(
            name = name,
            email = email,
            password = password,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        authService.login(
            email = email,
            password = password,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}
