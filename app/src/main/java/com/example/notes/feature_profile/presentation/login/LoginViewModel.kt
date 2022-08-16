package com.example.notes.feature_profile.presentation.login

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_notes.presentation.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val application: Application
): ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredLogin -> {
                _state.value = state.value.copy(
                    email = event.value
                )
            }
            is LoginEvent.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = event.value
                )
            }
            is LoginEvent.ClickLogin -> {
                if(_state.value.email.isNotBlank() && _state.value.password.isNotBlank()) {
                    if(_state.value.email.contains("@") && _state.value.email.contains(".")) {
                        auth.signInWithEmailAndPassword(_state.value.email, _state.value.password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(application, "You log in to app!", Toast.LENGTH_LONG).show()
                                    event.navController.popBackStack(
                                        route = Screen.Profile.route,
                                        inclusive = true
                                    )
                                } else {
                                    Toast.makeText(application, "Wrong email or password!", Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(application, "Email is incorrect!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(application, "Fill all fields!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}