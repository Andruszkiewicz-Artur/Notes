package com.example.notes.feature_profile.presentation.changeEmail

import com.example.notes.feature_profile.presentation.changePassword.UiEventChangePassword

sealed class UiEventChangeEmail {
    object ChangeEmail: UiEventChangeEmail()
}
