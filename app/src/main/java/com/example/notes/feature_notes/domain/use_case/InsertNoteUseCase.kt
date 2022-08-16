package com.example.notes.feature_notes.domain.use_case

import com.example.notes.notes_future.domain.model.InvalidNoteException
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.domain.repository.NoteRepository

class InsertNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("Title can`t be empty!")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("Content can`t be empty!")
        }
        repository.insertNote(note)
    }

}

