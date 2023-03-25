package com.example.notes.feature_profile.presentation.forgetPassword

import androidx.compose.ui.focus.FocusState
import androidx.navigation.NavHostController

sealed class ForgetPasswordEvent {
    data class EnteredEmail(val value: String): ForgetPasswordEvent()
    data class ChangeEmailFocus(val focusState: FocusState): ForgetPasswordEvent()
    object OnClickForgetPassword: ForgetPasswordEvent()
}
