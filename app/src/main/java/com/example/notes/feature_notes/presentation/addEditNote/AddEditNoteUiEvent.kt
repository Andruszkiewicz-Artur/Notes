package com.example.notes.feature_notes.presentation.addEditNote

sealed class AddEditNoteUiEvent {
    data object SaveNote: AddEditNoteUiEvent()
}
