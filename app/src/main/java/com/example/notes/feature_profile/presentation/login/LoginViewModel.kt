package com.example.notes.feature_profile.presentation.login

import android.app.Application
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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val application: Application,
    private val profileUseCases: ProfileUseCases
): ViewModel() {

    private val _email = mutableStateOf(TextFieldState(
        placeholder = R.string.Email
    ))
    val email: State<TextFieldState> = _email

    private val _password = mutableStateOf(TextFieldState(
        placeholder = R.string.Password
    ))
    val password: State<TextFieldState> = _password

    private val _eventFlow = MutableSharedFlow<UiEventLogin>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredLogin -> {
                _email.value = email.value.copy(
                    text = event.value
                )
            }
            is LoginEvent.ChangeLoginFocus -> {
                _email.value = email.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _email.value.text.isEmpty()
                )
            }
            is LoginEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            is LoginEvent.ChangePasswordFocus -> {
                _password.value = password.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _password.value.text.isEmpty()
                )
            }
            is LoginEvent.ClickLogin -> {
                viewModelScope.launch {
                    val loginResult = profileUseCases.logInUseCase.execute(
                        email = _email.value.text,
                        password = _password.value.text
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
    }
}