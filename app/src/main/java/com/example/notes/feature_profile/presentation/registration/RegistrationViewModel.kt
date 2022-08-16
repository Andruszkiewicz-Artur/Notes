package com.example.notes.feature_profile.presentation.registration

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_notes.presentation.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val application: Application
): ViewModel() {

    private val _state = mutableStateOf(RegistrationState())
    val state: State<RegistrationState> = _state

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = event.value
                )
            }
            is RegistrationEvent.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = event.value
                )
            }
            is RegistrationEvent.OnClickRules -> {
                _state.value = state.value.copy(
                    isRules = !_state.value.isRules
                )
            }
            is RegistrationEvent.OnClickRegistration -> {
                if(_state.value.email.isNotBlank() && _state.value.password.isNotBlank()) {
                    if(_state.value.email.contains("@") && _state.value.email.contains(".")) {
                        if(_state.value.password.length > 8) {
                            if(_state.value.isRules) {
                                auth.createUserWithEmailAndPassword(_state.value.email, _state.value.password)
                                    .addOnCompleteListener { task ->
                                        if(task.isSuccessful) {
                                            Toast.makeText(application, "You create account!", Toast.LENGTH_LONG).show()
                                            event.navController.popBackStack(
                                                route = Screen.Profile.route,
                                                inclusive = true
                                            )
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
            }
        }
    }

}