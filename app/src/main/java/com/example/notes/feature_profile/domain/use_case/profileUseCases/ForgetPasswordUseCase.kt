package com.example.notes.feature_profile.domain.use_case.profileUseCases

import com.example.notes.R
import com.example.notes.feature_profile.domain.repository.ProfileRepository
import com.example.notes.feature_profile.domain.unit.ValidationResult

class ForgetPasswordUseCase(
    private val repository: ProfileRepository
) {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.FillField.toString()
            )
        }

        return repository.ForgeinPassword(
            email = email
        )
    }

}