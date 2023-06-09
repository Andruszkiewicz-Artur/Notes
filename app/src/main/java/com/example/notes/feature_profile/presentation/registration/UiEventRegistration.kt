package com.example.notes.feature_profile.presentation.registration

import com.example.notes.feature_profile.presentation.login.UiEventLogin

sealed class UiEventRegistration {
    object Register: UiEventRegistration()
}