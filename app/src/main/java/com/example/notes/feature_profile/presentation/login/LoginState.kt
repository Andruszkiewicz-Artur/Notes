package com.example.notes.feature_profile.presentation.login

data class LoginState(
    val email: String = "",
    val emailErrorMessage: String? = null,
    val password: String = "",
    val passwordErrorMessage: String? = null,
    val isPasswordPresented: Boolean = false,
    val isLogIn: Boolean = false
)
