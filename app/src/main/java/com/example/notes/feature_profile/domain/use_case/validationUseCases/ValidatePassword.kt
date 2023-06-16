package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.notes.R
import com.example.notes.feature_profile.domain.unit.ValidationResult

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if (password.count() < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.LenghtPassword.toString()
            )
        }

        val containsLetterAndDigits = password.any{ it.isLetterOrDigit() }
        if (!containsLetterAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.DigitLetterPassword.toString()
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}