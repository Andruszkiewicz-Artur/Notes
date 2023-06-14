package com.example.notes.feature_notes.domain.use_case.remote

import com.example.notes.feature_notes.domain.model.RemoteNoteModel
import com.example.notes.feature_notes.domain.repository.NotesRemoteRepository
import com.example.notes.feature_profile.domain.unit.ValidationResult

class UploadNoteUseCase(
    private val repository: NotesRemoteRepository
) {

    suspend fun execute(remoteNoteModel: RemoteNoteModel): ValidationResult {
        return repository.uploadNotes(
            remoteNoteModel = remoteNoteModel
        )
    }

}