package com.example.notes.feature_profile.presentation.registration

data class RegistrationState(
    val email: String = "",
    val errorEmail: String? = null,
    val password: String = "",
    val errorPassword: String? = null,
    val rePassword: String = "",
    val errorRePassword: String? = null,
    val isTerms: Boolean = false,
    val errorTerms: String? = null,
    val isPresentedPassword: Boolean = false,
    val isRegistered: Boolean = false
)
