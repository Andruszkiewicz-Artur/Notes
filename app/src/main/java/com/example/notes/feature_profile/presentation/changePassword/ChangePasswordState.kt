package com.example.notes.feature_profile.presentation.changePassword

data class ChangePasswordState(
    val oldPassword: String = "",
    val newPassword: String = "",
    val errorNewPassword: String? = null,
    val newRePassword: String = "",
    val errorNewRePassword: String? = null
)
