package com.example.notes.feature_profile.presentation.changePassword

data class ChangePasswordState(
    val oldPassword: String = "",
    val newPassword: String = "",
    val newRePassword: String = "",
    val errorNewPassword: String? = null,
    val errorNewRePassword: String? = null,
    val isPresentedPassword: Boolean = false
)
