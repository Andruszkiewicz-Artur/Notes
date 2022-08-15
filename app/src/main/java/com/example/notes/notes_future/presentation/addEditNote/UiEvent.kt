package com.example.notes.notes_future.presentation.addEditNote

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
    object SaveNote: UiEvent()
}
