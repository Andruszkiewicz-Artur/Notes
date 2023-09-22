package com.example.notes.feature_profile.presentation.registration

import android.content.Context
import androidx.compose.ui.focus.FocusState
import androidx.navigation.NavHostController

sealed class RegistrationEvent {
    data class EnteredEmail(val value: String): RegistrationEvent()
    data class EnteredPassword(val value: String): RegistrationEvent()
    data class EnteredRePassword(val value: String): RegistrationEvent()
    data class CheckBox(val checkBox: Boolean): RegistrationEvent()
    data class OnClickRegistration(val context: Context): RegistrationEvent()
    object OnClickChangeVisibilityPassword: RegistrationEvent()
}
