package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.notes.R
import com.example.notes.feature_profile.domain.unit.ValidationResult

class ValidateTerms {

    fun execute(isAccepted: Boolean): ValidationResult {
        if(!isAccepted) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.TermsNotAccepted.toString()
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}