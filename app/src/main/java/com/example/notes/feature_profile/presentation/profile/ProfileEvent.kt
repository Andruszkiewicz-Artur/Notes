package com.example.notes.feature_profile.presentation.profile

sealed class ProfileEvent {
    object saveDataInCloud: ProfileEvent()
    object LogOut: ProfileEvent()
}
