package com.example.notes.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel (
    var isSynchronize: Boolean? = null
)