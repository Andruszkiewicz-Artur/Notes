package com.example.notes.feature_profile.domain.use_case.profileUseCases

import com.example.notes.feature_profile.domain.repository.ProfileRepository
import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.google.firebase.auth.FirebaseUser

class ChangeEmailUseCase(
    private val repository: ProfileRepository
) {
    suspend fun execute(
        user: FirebaseUser,
        oldEmail: String,
        newEmail: String,
        password: String
    ): ValidationResult {
        return repository.ChangeEmail(
            user = user,
            oldEmail = oldEmail,
            newEmail = newEmail,
            password = password
        )
    }

}