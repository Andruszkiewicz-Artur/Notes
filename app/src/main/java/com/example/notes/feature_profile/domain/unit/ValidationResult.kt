package com.example.notes.feature_profile.domain.unit

import android.app.Application
import androidx.core.text.isDigitsOnly

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)

fun decodeError(value: String?, application: Application): String? {
    if (value != null) {
        if (value.isDigitsOnly()) {
            return application.getString(value.toInt())
        }
    }

    return value
}
