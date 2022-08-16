package com.example.notes.feature_profile.presentation.profile

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.notes.feature_notes.presentation.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        val currentUser = auth.currentUser
        if(currentUser != null) {
            currentUser.let { user ->
                _state.value = state.value.copy(
                    email = user.email ?: "",
                    isUser = true
                )
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LogOut -> {

            }
        }
    }

}