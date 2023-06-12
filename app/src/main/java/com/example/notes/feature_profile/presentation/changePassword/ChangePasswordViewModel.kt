package com.example.notes.feature_profile.presentation.changePassword

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
import com.example.notes.feature_profile.presentation.login.UiEventLogin
import com.google.firebase.auth.EmailAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val application: Application,
    private val profileUseCases: ProfileUseCases,
    private val validateUseCases: ValidateUseCases
): ViewModel() {

    private val _oldPassword = mutableStateOf(TextFieldState(
        placeholder = "Old password..."
    )
    )
    val oldPassword: State<TextFieldState> = _oldPassword

    private val _newPassword = mutableStateOf(TextFieldState(
        placeholder = "New password..."
    )
    )
    val newPassword: State<TextFieldState> = _newPassword

    private val _rePassword = mutableStateOf(TextFieldState(
        placeholder = "Re-password..."
    )
    )
    val rePassword: State<TextFieldState> = _rePassword

    private val _state = mutableStateOf(ChangePasswordState())
    val state: State<ChangePasswordState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventChangePassword>()
    val eventFlow = _eventFlow

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.EnteredOldPassword -> {
                _oldPassword.value = oldPassword.value.copy(
                    text = event.text
                )
            }
            is ChangePasswordEvent.ChangeOldPasswordFocus -> {
                _oldPassword.value = oldPassword.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _oldPassword.value.text.isEmpty()
                )
            }
            is ChangePasswordEvent.EnteredNewPassword -> {
                _newPassword.value = newPassword.value.copy(
                    text = event.text
                )
            }
            is ChangePasswordEvent.ChangeNewPasswordFocus -> {
                _newPassword.value = newPassword.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _newPassword.value.text.isEmpty()
                )
            }
            is ChangePasswordEvent.EnteredRePassword -> {
                _rePassword.value = rePassword.value.copy(
                    text = event.text
                )
            }
            is ChangePasswordEvent.ChangeRePasswordFocus -> {
                _rePassword.value = rePassword.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _rePassword.value.text.isEmpty()
                )
            }
            is ChangePasswordEvent.ResetPassword -> {
                _state.value = state.value.copy(
                    oldPassword = _oldPassword.value.text,
                    newPassword = _newPassword.value.text,
                    newRePassword = _rePassword.value.text
                )

                val user = auth.currentUser

                if (isNoneErrors() && user != null) {
                   val changePasswordResult = profileUseCases.changePasswordUseCase.execute(
                        user = user,
                        email = user.email ?: "",
                        oldPassword = _state.value.oldPassword,
                        newPassword = _state.value.newPassword
                    )

                    if(!changePasswordResult.successful) {
                        Toast.makeText(application, changePasswordResult.errorMessage, Toast.LENGTH_LONG).show()
                    } else {
                        viewModelScope.launch {
                            _eventFlow.emit(UiEventChangePassword.ChangePassword)
                        }
                    }
                }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val password = validateUseCases.validatePassword.execute(_newPassword.value.text)
        val rePassword = validateUseCases.validateRePassword.execute(_newPassword.value.text, _rePassword.value.text)

        val hasError = listOf(
            password,
            rePassword
        ).any { !it.successful }

        if (hasError) {
            _state.value = state.value.copy(
                errorNewPassword = password.errorMessage,
                errorNewRePassword = password.errorMessage
            )
        }

        return !hasError
    }
}