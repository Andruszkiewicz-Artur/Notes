package com.example.notes.feature_profile.presentation.login

import androidx.compose.ui.focus.FocusState
import androidx.navigation.NavHostController

sealed class LoginEvent() {
    data class EnteredLogin(val value: String): LoginEvent()
    data class ChangeLoginFocus(val focusState: FocusState): LoginEvent()
    data class EnteredPassword(val value: String): LoginEvent()
    data class ChangePasswordFocus(val focusState: FocusState): LoginEvent()
    data class ClickLogin(val navController: NavHostController): LoginEvent()
    object loginViaGoogle: LoginEvent()
    object loginViaFacebook: LoginEvent()
    object loginViaGithub: LoginEvent()
}
