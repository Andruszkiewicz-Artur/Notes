package com.example.notes.feature_notes.presentation.addEditNote

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String): AddEditNoteEvent()
    data class EnteredContent(val value: String): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}
