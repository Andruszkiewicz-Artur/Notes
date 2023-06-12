package com.example.notes.feature_profile.domain.use_case.profileUseCases

import com.example.notes.feature_profile.domain.repository.ProfileRepository
import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.google.firebase.auth.FirebaseUser

class ChangePasswordUseCase(
    private val repository: ProfileRepository
) {

    fun execute(
        user: FirebaseUser,
        email: String,
        oldPassword: String,
        newPassword: String
    ): ValidationResult {
        return repository.ChangePassword(
            user = user,
            email = email,
            oldPassword = oldPassword,
            newPassword = newPassword
        )
    }

}