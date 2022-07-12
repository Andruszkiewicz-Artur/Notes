package com.example.notes.notes_future.domain.use_case

import com.example.notes.notes_future.domain.model.Note
import com.example.notes.notes_future.domain.repository.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }

}