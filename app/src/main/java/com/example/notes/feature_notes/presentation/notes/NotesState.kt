package com.example.notes.feature_notes.presentation.notes

import com.example.notes.feature_notes.domain.model.GridCellEnum
import com.example.notes.notes_future.domain.model.Note

data class NotesState(
    val allNotes: List<Note> = emptyList(),
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val typeOfPresentingList: GridCellEnum = GridCellEnum.Flat
)
