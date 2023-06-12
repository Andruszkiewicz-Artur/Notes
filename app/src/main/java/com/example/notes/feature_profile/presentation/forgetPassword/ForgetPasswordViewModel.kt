package com.example.notes.feature_profile.presentation.forgetPassword

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.feature_profile.domain.use_case.ValidateUseCases
import com.example.notes.feature_profile.presentation.profile.ProfileEvent
import com.example.notes.feature_profile.presentation.registration.RegistrationEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val application: Application,
    private val validateUseCases: ValidateUseCases
): ViewModel() {

    private val _email = mutableStateOf(TextFieldState(
        placeholder = "Email..."
    ))
    val email: State<TextFieldState> = _email

    private val _state = mutableStateOf(ForgetPasswordState())
    val state: State<ForgetPasswordState> = _state

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
                    Firebase.auth.sendPasswordResetEmail(_state.value.email)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                Toast.makeText(application, "Email sent!", Toast.LENGTH_LONG).show()
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