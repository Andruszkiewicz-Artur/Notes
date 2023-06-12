package com.example.notes.feature_profile.domain.unit

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
