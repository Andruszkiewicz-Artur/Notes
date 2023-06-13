package com.example.notes.feature_notes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteNoteModel(
    val id: Int,
    val value: RemoteContentNoteModel
)
