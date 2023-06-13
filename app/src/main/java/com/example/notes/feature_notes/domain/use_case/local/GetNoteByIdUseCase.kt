package com.example.notes.feature_notes.domain.use_case.local

import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.domain.repository.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }

}