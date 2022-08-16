package com.example.notes.feature_profile.presentation.login

import androidx.navigation.NavHostController

sealed class LoginEvent() {
    data class EnteredLogin(val value: String): LoginEvent()
    data class EnteredPassword(val value: String): LoginEvent()
    data class ClickLogin(val navController: NavHostController): LoginEvent()
}
