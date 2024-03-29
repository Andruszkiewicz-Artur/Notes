package com.example.notes.feature_notes.domain.use_case.remote

import com.example.notes.feature_notes.data.mapper.toRemoteNote
import com.example.notes.feature_notes.domain.model.RemoteNoteModel
import com.example.notes.feature_notes.domain.repository.NotesRemoteRepository
import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.example.notes.notes_future.domain.model.Note

class UploadNoteUseCase(
    private val repository: NotesRemoteRepository
) {

    suspend fun execute(note: Note): ValidationResult {
        return repository.uploadNotes(
            remoteNoteModel = note.toRemoteNote()
        )
    }

}