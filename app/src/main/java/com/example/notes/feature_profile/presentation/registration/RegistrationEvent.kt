package com.example.notes.feature_profile.presentation.registration

import androidx.navigation.NavHostController

sealed class RegistrationEvent {
    data class EnteredEmail(val value: String): RegistrationEvent()
    data class EnteredPassword(val value: String): RegistrationEvent()
    object OnClickRules: RegistrationEvent()
    data class OnClickRegistration(val navController: NavHostController): RegistrationEvent()
}
