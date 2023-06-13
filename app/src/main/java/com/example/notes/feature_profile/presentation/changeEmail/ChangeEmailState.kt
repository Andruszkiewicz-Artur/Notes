package com.example.notes.feature_profile.presentation.changeEmail

data class ChangeEmailState(
    val password: String = "",
    val email: String = "",
    val errorEmail: String? = null
)