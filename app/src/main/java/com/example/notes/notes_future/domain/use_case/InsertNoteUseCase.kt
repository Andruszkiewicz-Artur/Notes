package com.example.notes.notes_future.domain.use_case

import com.example.notes.notes_future.domain.model.Note
import com.example.notes.notes_future.domain.repository.NoteRepository

class InsertNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.insertNote(note)
    }

}