package com.example.notes.feature_profile.presentation.forgetPassword

import androidx.navigation.NavHostController

sealed class ForgetPasswordEvent {
    data class EnteredEmail(val value: String): ForgetPasswordEvent()
    data class OnClickForgetPassword(val navController: NavHostController): ForgetPasswordEvent()
}
