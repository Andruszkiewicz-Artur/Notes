package com.example.notes.feature_notes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteContentNoteModel(
    val title: String,
    val content: String,
    val updateTime: Long,
    val isDeleted: Boolean
)