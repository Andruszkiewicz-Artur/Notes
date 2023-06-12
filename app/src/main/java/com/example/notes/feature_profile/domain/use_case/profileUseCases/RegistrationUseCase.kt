package com.example.notes.feature_profile.domain.use_case.profileUseCases

import com.example.notes.feature_profile.domain.repository.ProfileRepository
import com.example.notes.feature_profile.domain.unit.ValidationResult

class RegistrationUseCase(
    private val repository: ProfileRepository
) {

    fun execute(email: String, password: String): ValidationResult {
        return repository.Registration(
            email = email,
            password = password
        )
    }

}