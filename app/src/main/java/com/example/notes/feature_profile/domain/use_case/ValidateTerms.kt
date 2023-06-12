package com.example.notes.feature_profile.domain.use_case

import com.example.notes.feature_profile.domain.unit.ValidationResult

class ValidateTerms {

    fun execute(isAccepted: Boolean): ValidationResult {
        if(!isAccepted) {
            return ValidationResult(
                successful = false,
                errorMessage = "Terms are not accepted"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}