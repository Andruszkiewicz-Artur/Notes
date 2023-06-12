package com.example.notes.feature_profile.presentation.login

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_profile.domain.use_case.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
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
                    auth.signInWithEmailAndPassword(_email.value.text, _password.value.text)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                event.navController.popBackStack(
                                    route = Screen.Profile.route,
                                    inclusive = true
                                )
                            }
                        }
                }
            }
        }
    }
}