package com.example.notes.notes_future.presentation.notes

import com.example.notes.notes_future.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList()
)
