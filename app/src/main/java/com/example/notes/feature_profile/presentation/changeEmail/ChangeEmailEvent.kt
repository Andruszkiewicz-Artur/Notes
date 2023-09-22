package com.example.notes.feature_profile.presentation.changeEmail

import android.content.Context
import androidx.compose.ui.focus.FocusState

sealed class ChangeEmailEvent {
    data class EnteredPassword(val value: String): ChangeEmailEvent()
    data class EnteredEmail(val value: String): ChangeEmailEvent()
    data class ChangeEmail(val context: Context): ChangeEmailEvent()
    object ChangePasswordPresentation: ChangeEmailEvent()
}
