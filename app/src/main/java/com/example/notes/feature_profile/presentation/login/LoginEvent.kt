package com.example.notes.feature_profile.presentation.login

import android.content.Context
import androidx.compose.ui.focus.FocusState
import androidx.navigation.NavHostController

sealed class LoginEvent {
    data class EnteredLogin(val value: String): LoginEvent()
    data class EnteredPassword(val value: String): LoginEvent()
    data class ClickLogin(val context: Context): LoginEvent()

    object ChangeVisibilityPassword: LoginEvent()
}
