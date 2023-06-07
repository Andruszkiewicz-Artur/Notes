package com.example.notes.feature_profile.presentation.profile

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_notes.presentation.database
import com.example.notes.feature_profile.presentation.login.UiEventLogin
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
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

    private val _eventFlow = MutableSharedFlow<UiEventProfile>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.saveDataInCloud -> {

            }
            is ProfileEvent.LogOut -> {
                viewModelScope.launch {
                    auth.signOut()
                    _state.value = state.value.copy(
                        email = "",
                        isUser = false
                    )

                    _eventFlow.emit(UiEventProfile.LogOut)
                }
            }
        }
    }

}