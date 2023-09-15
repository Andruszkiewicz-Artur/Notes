package com.example.notes.feature_profile.presentation.changeEmail

import android.app.Application
import android.content.Context
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
class ChangeEmailViewModel @Inject constructor(
    private val application: Application,
    private val profileUseCases: ProfileUseCases,
    private val validateUseCases: ValidateUseCases
): ViewModel() {
    private val _state = MutableStateFlow(ChangeEmailState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEventChangeEmail>()
    val eventFlow = _eventFlow

    fun onEvent(event: ChangeEmailEvent) {
        when (event) {
            is ChangeEmailEvent.EnteredEmail -> {
                _state.update { it.copy(
                    email = event.value
                ) }
            }
            is ChangeEmailEvent.EnteredPassword -> {
                _state.update { it.copy(
                    password = event.value
                ) }
            }
            is ChangeEmailEvent.ChangeEmail -> {
                val user = auth.currentUser

                if (isNoneError() && user != null) {
                    viewModelScope.launch {
                        val changeEmailResult = profileUseCases.changeEmailUseCase.execute(
                            user = user,
                            oldEmail = user.email ?: "",
                            newEmail = _state.value.email,
                            password = _state.value.password
                        )

                        if(!changeEmailResult.successful) {
                            Toast.makeText(application, decodeError(changeEmailResult.errorMessage, application), Toast.LENGTH_LONG).show()
                        } else {
                            viewModelScope.launch {
                                _eventFlow.emit(UiEventChangeEmail.ChangeEmail)
                            }
                        }
                    }
                }
            }
            ChangeEmailEvent.ChangePasswordPresentation -> {
                _state.update { it.copy(
                    isPresentedPassword = it.isPresentedPassword.not()
                ) }
            }
        }
    }

    private fun isNoneError(): Boolean {
        val password = validateUseCases.validatePassword.execute(_state.value.password)
        val email = validateUseCases.validateEmail.execute(_state.value.email)

        val hasError = !email.successful && !password.successful

        if (hasError) {
            _state.value = state.value.copy(
                errorPassword = decodeError(password.errorMessage, application),
                errorEmail = decodeError(email.errorMessage, application)
            )
        }

        return !hasError
    }
}