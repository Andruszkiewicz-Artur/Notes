package com.example.notes.feature_notes.domain.use_case.local

import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(): Flow<List<Note>> {
        return repository.getAllNotes()
    }

}