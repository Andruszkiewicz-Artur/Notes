package com.example.notes.feature_notes.presentation.addEditNote

data class AddEditNoteState(
    val title: String = "",
    val content: String = "",
    val noteIsSaved: Boolean = false
)
