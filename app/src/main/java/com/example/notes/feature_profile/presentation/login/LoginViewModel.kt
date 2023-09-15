package com.example.notes.feature_profile.presentation.login

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.R
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.core.model.ProfileModel
import com.example.notes.core.value.profileSetting
import com.example.notes.feature_profile.domain.unit.decodeError
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val application: Application,
    private val profileUseCases: ProfileUseCases
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEventLogin>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredLogin -> {
                _state.update { it.copy(
                    email = event.value
                ) }
            }
            is LoginEvent.EnteredPassword -> {
                _state.update { it.copy(
                    password = event.value
                ) }
            }
            is LoginEvent.ClickLogin -> {
                if (isNoneErrors()) {
                    viewModelScope.launch {
                        val loginResult = profileUseCases.logInUseCase.execute(
                            email = _state.value.email,
                            password = _state.value.password
                        )

                        if(!loginResult.successful) {
                            Toast.makeText(application, decodeError(loginResult.errorMessage, application), Toast.LENGTH_LONG).show()
                        } else {
                            profileSetting = ProfileModel()
                            viewModelScope.launch {
                                _eventFlow.emit(UiEventLogin.LogIn)
                            }
                        }
                    }
                }
            }
            LoginEvent.ChangeVisibilityPassword -> {
                _state.update { it.copy(
                    isPasswordPresented = it.isPasswordPresented.not()
                ) }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val email = validateUseCases.validateEmail.execute(_state.value.email)
        val password = validateUseCases.validatePassword.execute(_state.value.password)

        val hasError = listOf(
            email,
            password
        ).any { !it.successful }

        if (hasError) {
            _state.value = state.value.copy(
                emailErrorMessage = decodeError(email.errorMessage, application),
                passwordErrorMessage = decodeError(password.errorMessage, application),
            )
        }

        Log.d("has error", hasError.toString())

        return !hasError
    }
}