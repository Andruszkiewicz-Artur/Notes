package com.example.notes.feature_profile.presentation.changePassword

import androidx.compose.ui.focus.FocusState

sealed class ChangePasswordEvent {
    data class EnteredOldPassword(val text: String): ChangePasswordEvent()
    data class ChangeOldPasswordFocus(val focusState: FocusState): ChangePasswordEvent()
    data class EnteredNewPassword(val text: String): ChangePasswordEvent()
    data class ChangeNewPasswordFocus(val focusState: FocusState): ChangePasswordEvent()
    data class EnteredRePassword(val text: String): ChangePasswordEvent()
    data class ChangeRePasswordFocus(val focusState: FocusState): ChangePasswordEvent()
    object ResetPassword: ChangePasswordEvent()
}
