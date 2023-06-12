package com.example.notes.feature_profile.domain.use_case

data class ValidateUseCases(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateRePassword: ValidateRePassword,
    val validateTerms: ValidateTerms
)
