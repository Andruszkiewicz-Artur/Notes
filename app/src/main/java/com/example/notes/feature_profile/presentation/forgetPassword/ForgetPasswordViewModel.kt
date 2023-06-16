package com.example.notes.feature_profile.presentation.forgetPassword

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.R
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import com.example.notes.feature_profile.presentation.login.UiEventLogin
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val application: Application,
    private val validateUseCases: ValidateUseCases,
    private val profileUseCases: ProfileUseCases
): ViewModel() {

    private val _email = mutableStateOf(TextFieldState(
        placeholder = R.string.Email.toString()
    ))
    val email: State<TextFieldState> = _email

    private val _state = mutableStateOf(ForgetPasswordState())
    val state: State<ForgetPasswordState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventForgetPassword>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: ForgetPasswordEvent) {
        when (event) {
            is ForgetPasswordEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.value
                )
            }
            is ForgetPasswordEvent.ChangeEmailFocus -> {
                _email.value = email.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _email.value.text.isEmpty()
                )
            }
            is ForgetPasswordEvent.OnClickForgetPassword -> {
                _state.value = state.value.copy(
                    email = _email.value.text
                )

                if(isNoneError()) {
                    val loginResult = profileUseCases.forgetPasswordUseCase.execute(
                        email = _state.value.email
                    )

                    if(!loginResult.successful) {
                        Toast.makeText(application, loginResult.errorMessage, Toast.LENGTH_LONG).show()
                    } else {
                        viewModelScope.launch {
                            _eventFlow.emit(UiEventForgetPassword.ClickForgetPassword)
                        }
                    }
                }
            }
        }
    }

    fun isNoneError(): Boolean {
        val email = validateUseCases.validateEmail.execute(_email.value.text)

        if (!email.successful) {
            _state.value = state.value.copy(
                errorEmail = email.errorMessage
            )
        }

        return email.successful
    }
}