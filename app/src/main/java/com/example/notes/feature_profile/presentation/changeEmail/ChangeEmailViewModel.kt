package com.example.notes.feature_profile.presentation.changeEmail

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import com.example.notes.feature_profile.presentation.changePassword.UiEventChangePassword
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeEmailViewModel @Inject constructor(
    private val application: Application,
    private val profileUseCases: ProfileUseCases,
    private val validateUseCases: ValidateUseCases
): ViewModel() {

    private val _password = mutableStateOf(TextFieldState(
        placeholder = "Current password..."
    ))
    val password: State<TextFieldState> = _password

    private val _email = mutableStateOf(TextFieldState(
        placeholder = "New email..."
    ))
    val email: State<TextFieldState> = _email

    private val _state = mutableStateOf(ChangeEmailState())
    val state: State<ChangeEmailState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventChangeEmail>()
    val eventFlow = _eventFlow

    fun onEvent(event: ChangeEmailEvent) {
        when (event) {
            is ChangeEmailEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.value
                )
            }
            is ChangeEmailEvent.ChangeEmailFocus -> {
                _email.value = email.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _email.value.text.isEmpty()
                )
            }
            is ChangeEmailEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            is ChangeEmailEvent.ChangePasswordFocus -> {
                _password.value = password.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _password.value.text.isEmpty()
                )
            }
            is ChangeEmailEvent.ChangeEmail -> {
                _state.value = state.value.copy(
                    password = password.value.text,
                    email = email.value.text
                )

                val user = auth.currentUser

                if (isNoneError() && user != null) {
                    val changeEmailResult = profileUseCases.changeEmailUseCase.execute(
                        user = user,
                        oldEmail = user.email ?: "",
                        newEmail = _state.value.email,
                        password = _state.value.password
                    )

                    if(!changeEmailResult.successful) {
                        Toast.makeText(application, changeEmailResult.errorMessage, Toast.LENGTH_LONG).show()
                    } else {
                        viewModelScope.launch {
                            _eventFlow.emit(UiEventChangeEmail.ChangeEmail)
                        }
                    }
                }
            }
        }
    }

    private fun isNoneError(): Boolean {
        val email = validateUseCases.validateEmail.execute(_state.value.email)

        val hasError = !email.successful

        if (hasError) {
            _state.value = state.value.copy(
                errorEmail = email.errorMessage
            )
        }

        return !hasError
    }
}