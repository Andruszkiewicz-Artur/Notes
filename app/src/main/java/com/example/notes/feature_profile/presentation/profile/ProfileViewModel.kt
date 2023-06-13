package com.example.notes.feature_profile.presentation.profile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.serializer.SettingSerializer
import com.example.notes.feature_notes.domain.unit.Resource
import com.example.notes.feature_notes.domain.use_case.remote.RemoteUseCases
import com.example.notes.feature_notes.presentation.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val remoteUseCases: RemoteUseCases
): ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventProfile>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.saveDataInCloud -> {
                _state.value = state.value.copy(
                    isSynchronized = event.isSaved
                )
                val result = remoteUseCases.setUpSynchronizeUseCase.execute(_state.value.isSynchronized)
                Log.d("Result set up synchronize", result.errorMessage ?: "None")
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

    fun initFunc() {
        val currentUser = auth.currentUser
        if(currentUser != null) {
            viewModelScope.launch {
                val result = remoteUseCases.checkIsSynchronize.execute()
                Log.d("result in profile", result.data.toString())

                when (result) {
                    is Resource.Error -> {
                        Log.d("Error isSynchronize", result.message.toString())
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isSynchronized = result.data ?: _state.value.isSynchronized
                        )
                    }
                }
            }

            currentUser.let { user ->
                _state.value = state.value.copy(
                    email = user.email ?: "",
                    isUser = true
                )
            }
        }
    }
}