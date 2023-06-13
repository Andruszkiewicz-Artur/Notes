package com.example.notes.feature_notes.domain.use_case.local

import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }

}