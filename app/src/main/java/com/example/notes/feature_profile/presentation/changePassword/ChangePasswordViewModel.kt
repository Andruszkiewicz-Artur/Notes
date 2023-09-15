package com.example.notes.feature_profile.presentation.changePassword

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.R
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_profile.domain.unit.decodeError
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val application: Application,
    private val profileUseCases: ProfileUseCases,
    private val validateUseCases: ValidateUseCases
): ViewModel() {
    private val _state = MutableStateFlow(ChangePasswordState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEventChangePassword>()
    val eventFlow = _eventFlow

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.EnteredOldPassword -> {
                _state.update { it.copy(
                    oldPassword = event.text
                ) }
            }
            is ChangePasswordEvent.EnteredNewPassword -> {
                _state.update { it.copy(
                    newPassword = event.text
                ) }
            }
            is ChangePasswordEvent.EnteredRePassword -> {
                _state.update { it.copy(
                    newRePassword = event.text
                ) }
            }
            is ChangePasswordEvent.ResetPassword -> {
                val user = auth.currentUser

                if (isNoneErrors() && user != null) {
                   viewModelScope.launch {
                       val changePasswordResult = profileUseCases.changePasswordUseCase.execute(
                           user = user,
                           email = user.email ?: "",
                           oldPassword = _state.value.oldPassword,
                           newPassword = _state.value.newPassword
                       )

                       if(!changePasswordResult.successful) {
                           Toast.makeText(application, decodeError(changePasswordResult.errorMessage, application), Toast.LENGTH_LONG).show()
                       } else {
                           viewModelScope.launch {
                               _eventFlow.emit(UiEventChangePassword.ChangePassword)
                           }
                       }
                   }
                }
            }
            ChangePasswordEvent.ChangeVisibilityPassword -> {
                _state.update { it.copy(
                    isPresentedPassword = it.isPresentedPassword.not()
                ) }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val password = validateUseCases.validatePassword.execute(_state.value.newPassword)
        val rePassword = validateUseCases.validateRePassword.execute(_state.value.newPassword, _state.value.newRePassword)

        val hasError = listOf(
            password,
            rePassword
        ).any { !it.successful }

        if (hasError) {
            _state.value = state.value.copy(
                errorNewPassword = decodeError(password.errorMessage, application),
                errorNewRePassword = decodeError(password.errorMessage, application)
            )
        }

        return !hasError
    }
}