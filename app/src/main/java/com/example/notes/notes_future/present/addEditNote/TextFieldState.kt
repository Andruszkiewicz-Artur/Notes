package com.example.notes.notes_future.present.addEditNote

import androidx.compose.ui.focus.FocusState

data class TextFieldState(
    val text: String = "",
    val placeholder: String = "",
    val isPlaceholder: Boolean = true
)
