package com.example.notes.feature_notes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteNoteModel(
    val id: String,
    val value: RemoteContentNoteModel
)
