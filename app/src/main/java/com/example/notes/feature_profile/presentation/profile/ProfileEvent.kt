package com.example.notes.feature_profile.presentation.profile

sealed class ProfileEvent {
    data class saveDataInCloud(val isSaved: Boolean): ProfileEvent()
    object LogOut: ProfileEvent()
}
