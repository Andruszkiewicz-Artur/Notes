package com.example.notes.feature_notes.presentation.addEditNote

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
    object SaveNote: UiEvent()
}
