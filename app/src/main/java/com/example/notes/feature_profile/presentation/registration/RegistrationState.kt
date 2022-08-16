package com.example.notes.feature_profile.presentation.registration

data class RegistrationState(
    val email: String = "",
    val password: String = "",
    val isRules: Boolean = false
)
