package com.example.notes.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Settings (
    val isSynchronize: Boolean = false
)