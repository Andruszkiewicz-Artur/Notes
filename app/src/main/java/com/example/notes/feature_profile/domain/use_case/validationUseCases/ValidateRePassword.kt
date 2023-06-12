package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.notes.feature_profile.domain.unit.ValidationResult

class ValidateRePassword {

    fun execute(password: String, rePassword: String): ValidationResult {
        if(password != rePassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "Passwords are not the same"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}