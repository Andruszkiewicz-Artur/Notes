package com.example.notes.feature_profile.domain.use_case.profileUseCases

import com.example.notes.feature_profile.domain.repository.ProfileRepository
import com.example.notes.feature_profile.domain.unit.ValidationResult

class LogInUseCase(
    private val repository: ProfileRepository
) {

    fun execute(email: String, password: String): ValidationResult {
        if (email.isBlank() || password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Fill all fields"
            )
        }

        return repository.LogIn(
            email = email,
            password = password
        )
    }

}