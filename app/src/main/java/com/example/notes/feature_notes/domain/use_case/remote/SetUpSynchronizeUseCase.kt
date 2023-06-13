package com.example.notes.feature_notes.domain.use_case.remote

import com.example.notes.feature_notes.domain.repository.NotesRemoteRepository
import com.example.notes.feature_profile.domain.unit.ValidationResult

class SetUpSynchronizeUseCase(
    private val repository: NotesRemoteRepository
) {

    fun execute(isSynchronized: Boolean): ValidationResult {
        return repository.setUpSynchronize(
            isSynchronized = isSynchronized
        )
    }

}