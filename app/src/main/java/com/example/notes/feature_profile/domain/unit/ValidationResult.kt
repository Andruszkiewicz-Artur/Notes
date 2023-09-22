package com.example.notes.feature_profile.domain.unit

import android.app.Application
import android.content.Context
import androidx.core.text.isDigitsOnly

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)

fun decodeError(value: String?, context: Context): String? {
    if (value != null) {
        if (value.isDigitsOnly()) {
            return context.getString(value.toInt())
        }
    }

    return value
}
