package com.example.notes.feature_profile.domain.use_case.profileUseCases

import com.example.notes.feature_profile.domain.repository.ProfileRepository
import com.example.notes.feature_profile.domain.unit.ValidationResult

class LogOutUseCase(
    private val repository: ProfileRepository
) {

    fun execute(): ValidationResult {
        return repository.LogOut()
    }

}