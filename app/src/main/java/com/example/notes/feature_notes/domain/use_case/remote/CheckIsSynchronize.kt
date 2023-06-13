package com.example.notes.feature_notes.domain.use_case.remote

import com.example.notes.feature_notes.domain.repository.NotesRemoteRepository
import com.example.notes.feature_notes.domain.unit.Resource

class CheckIsSynchronize(
    private val repository: NotesRemoteRepository
) {
    suspend fun execute(): Resource<Boolean> {
        return repository.checkIsSynchronize()
    }
}