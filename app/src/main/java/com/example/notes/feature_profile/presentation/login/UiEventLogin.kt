package com.example.notes.feature_profile.presentation.login

sealed class UiEventLogin {
    data class ShowSnackbar(val message: String): UiEventLogin()
    object LogIn: UiEventLogin()
}
