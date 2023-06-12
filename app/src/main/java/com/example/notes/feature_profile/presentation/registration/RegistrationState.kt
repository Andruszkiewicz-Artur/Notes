package com.example.notes.feature_profile.presentation.registration

data class RegistrationState(
    val email: String = "",
    val erroeEmail: String? = null,
    val password: String = "",
    val errorPassword: String? = null,
    val rePassword: String = "",
    val errorRePassword: String? = null,
    val isTerms: Boolean = false,
    val errorTerms: String? = null
)
