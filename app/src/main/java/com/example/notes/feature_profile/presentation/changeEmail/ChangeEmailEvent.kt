package com.example.notes.feature_profile.presentation.changeEmail

import androidx.compose.ui.focus.FocusState

sealed class ChangeEmailEvent {
    data class EnteredPassword(val value: String): ChangeEmailEvent()
    data class ChangePasswordFocus(val focusState: FocusState): ChangeEmailEvent()
    data class EnteredEmail(val value: String): ChangeEmailEvent()
    data class ChangeEmailFocus(val focusState: FocusState): ChangeEmailEvent()
    object ChangeEmail: ChangeEmailEvent()
}
