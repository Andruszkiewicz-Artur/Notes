package com.example.notes.notes_future.present.addEditNote

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
    object SaveNote: UiEvent()
}
