package com.example.notes.feature_profile.presentation.registration

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.compose.checkBox.CheckBoxState
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.core.model.ProfileModel
import com.example.notes.core.value.profileSetting
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import com.example.notes.feature_profile.presentation.login.UiEventLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val application: Application,
    private val profileUseCases: ProfileUseCases
): ViewModel() {

    private val _email = mutableStateOf(TextFieldState(
        placeholder = "Email..."
    ))
    val email: State<TextFieldState> = _email

    private val _password = mutableStateOf(TextFieldState(
        placeholder = "Password..."
    ))
    val password: State<TextFieldState> = _password

    private val _rePassword = mutableStateOf(TextFieldState(
        placeholder = "Re-password..."
    ))
    val rePassword: State<TextFieldState> = _rePassword

    private val _checkBox = mutableStateOf(
        CheckBoxState(
        text = "Accept rules of app."
    )
    )
    val checkBox: State<CheckBoxState> = _checkBox

    private val _state = mutableStateOf(RegistrationState())
    val state: State<RegistrationState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventRegistration>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.value
                )
            }
            is RegistrationEvent.ChangeFocusEmail -> {
                _email.value = email.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _email.value.text.isEmpty()
                )
            }
            is RegistrationEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            is RegistrationEvent.ChangeFocusPassword -> {
                _password.value = password.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _password.value.text.isEmpty()
                )
            }
            is RegistrationEvent.EnteredRePassword -> {
                _rePassword.value = rePassword.value.copy(
                    text = event.value
                )
            }
            is RegistrationEvent.ChangeFocusRePassword -> {
                _rePassword.value = rePassword.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _rePassword.value.text.isEmpty()
                )
            }
            is RegistrationEvent.CheckBox -> {
                   _checkBox.value = checkBox.value.copy(
                       isChacked = event.checkBox
                   )
            }
            is RegistrationEvent.OnClickRegistration -> {
                _state.value = state.value.copy(
                    email = _email.value.text,
                    password = _password.value.text,
                    rePassword = _rePassword.value.text,
                    isTerms = _checkBox.value.isChacked
                )

                if (isNoneErrors()) {
                    val registrationResult = profileUseCases.registrationUseCase.execute(
                        email = _email.value.text,
                        password = _password.value.text
                    )

                    if(!registrationResult.successful) {
                        Toast.makeText(application, registrationResult.errorMessage, Toast.LENGTH_LONG).show()
                    } else {
                        profileSetting = ProfileModel()
                        viewModelScope.launch {
                            _eventFlow.emit(UiEventRegistration.Register)
                        }
                    }
                }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val email = validateUseCases.validateEmail.execute(_state.value.email)
        val password = validateUseCases.validatePassword.execute(_state.value.password)
        val rePassword = validateUseCases.validateRePassword.execute(_state.value.password, _state.value.rePassword)
        val terms = validateUseCases.validateTerms.execute(_state.value.isTerms)

        val hasError = listOf(
            email,
            password,
            rePassword,
            terms
        ).any { !it.successful }

        if (hasError) {
            _state.value = state.value.copy(
                erroeEmail = email.errorMessage,
                errorPassword = password.errorMessage,
                errorRePassword = rePassword.errorMessage,
                errorTerms = terms.errorMessage
            )
        }

        Log.d("has error", hasError.toString())

        return !hasError
    }
}