package com.example.notes.feature_profile.presentation.registration

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.compose.checkBox.CheckBoxState
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_profile.presentation.login.UiEventLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val application: Application
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
                    isRules = _checkBox.value.isChacked
                )

                if (_state.value.isRules) {
                    if(_state.value.email.isNotBlank() && _state.value.password.isNotBlank()) {
                        if(_state.value.email.contains("@") && _state.value.email.contains(".")) {
                            if(_state.value.password.length >= 8) {
                                if(_state.value.isRules) {
                                    auth.createUserWithEmailAndPassword(_state.value.email, _state.value.password)
                                        .addOnCompleteListener { task ->
                                            if(task.isSuccessful) {
                                                Toast.makeText(application, "You create account!", Toast.LENGTH_LONG).show()
                                                viewModelScope.launch {
                                                    _eventFlow.emit(
                                                        UiEventRegistration.Register
                                                    )
                                                }
                                            } else {
                                                Toast.makeText(application, "You can`t create account!", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                } else {
                                    Toast.makeText(application, "You need check rules!", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                Toast.makeText(application, "Password need at least 8 chars!", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(application, "Email is incorrect!", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(application, "You need first fill all fields!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(application, "You need accept rules", Toast.LENGTH_LONG)
                }
            }
        }
    }

}