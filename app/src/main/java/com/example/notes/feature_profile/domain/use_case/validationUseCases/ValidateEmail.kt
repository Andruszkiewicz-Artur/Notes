package com.example.notes.feature_profile.domain.use_case.validationUseCases

import android.util.Patterns
import com.example.notes.R
import com.example.notes.feature_profile.domain.unit.ValidationResult

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.FieldIsEmpty.toString()
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.EmailIsIncorrect.toString()
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}