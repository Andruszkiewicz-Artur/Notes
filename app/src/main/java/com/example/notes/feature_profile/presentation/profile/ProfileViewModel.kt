package com.example.notes.feature_profile.presentation.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.util.rangeTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.value.profileSetting
import com.example.notes.feature_notes.domain.unit.Resource
import com.example.notes.feature_notes.domain.use_case.remote.RemoteUseCases
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val remoteUseCases: RemoteUseCases,
    private val profileUseCases: ProfileUseCases
): ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.saveDataInCloud -> {
                _state.value = state.value.copy(
                    isSynchronized = event.isSaved
                )
                val result = remoteUseCases.setUpSynchronizeUseCase.execute(_state.value.isSynchronized)
                profileSetting?.isSynchronize = _state.value.isSynchronized
            }
            is ProfileEvent.LogOut -> {
                viewModelScope.launch {
                    val result = profileUseCases.logOutUseCase.execute()

                    if(result.successful) {
                        _state.value = state.value.copy(
                            email = "",
                            isUser = false
                        )

                        profileSetting = null
                    } else {
                        Log.d("Problem with login", result.errorMessage.toString())
                    }
                }
            }
        }
    }

    fun initFunc() {
        val currentUser = auth.currentUser
        if(currentUser != null) {
            if (profileSetting?.isSynchronize == null) {
                viewModelScope.launch {
                    val result = remoteUseCases.checkIsSynchronize.execute()

                    when (result) {
                        is Resource.Error -> {
                            Log.d("Error isSynchronize", result.message.toString())
                        }
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                isSynchronized = result.data ?: _state.value.isSynchronized
                            )
                            profileSetting?.isSynchronize = result.data
                        }
                    }
                }
            }

            currentUser.let { user ->
                _state.value = state.value.copy(
                    email = user.email ?: "",
                    isUser = true,
                    isSynchronized = profileSetting?.isSynchronize ?: false
                )
            }
        }
    }
}