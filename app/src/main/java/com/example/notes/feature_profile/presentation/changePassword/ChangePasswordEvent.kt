package com.example.notes.feature_profile.presentation.changePassword

import android.content.Context
import androidx.compose.ui.focus.FocusState

sealed class ChangePasswordEvent {
    data class EnteredOldPassword(val text: String): ChangePasswordEvent()
    data class EnteredNewPassword(val text: String): ChangePasswordEvent()
    data class EnteredRePassword(val text: String): ChangePasswordEvent()
    data class ResetPassword(val context: Context): ChangePasswordEvent()
    object ChangeVisibilityPassword: ChangePasswordEvent()
}
