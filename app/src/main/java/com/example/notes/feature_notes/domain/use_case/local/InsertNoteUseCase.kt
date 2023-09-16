package com.example.notes.feature_notes.domain.use_case.local

import com.example.notes.R
import com.example.notes.notes_future.domain.model.InvalidNoteException
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.domain.repository.NoteRepository

class InsertNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.insertNote(note)
    }

}

