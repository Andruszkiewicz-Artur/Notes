package com.example.notes.feature_profile.presentation.changeEmail

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.feature_notes.presentation.auth
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeEmailViewModel @Inject constructor(
    private val application: Application
): ViewModel() {

    private val _password = mutableStateOf(TextFieldState(
        placeholder = "Current password..."
    ))
    val password: State<TextFieldState> = _password

    private val _email = mutableStateOf(TextFieldState(
        placeholder = "New email..."
    ))
    val email: State<TextFieldState> = _email

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
                if (_password.value.text.isNotEmpty() && _email.value.text.isNotEmpty()) {
                    if(_email.value.text.contains(".") && _email.value.text.contains("@")) {
                        if (auth.currentUser != null) {
                            val credential = EmailAuthProvider
                                .getCredential(auth.currentUser!!.email!!, _password.value.text)

                            auth.currentUser!!.reauthenticate(credential)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        auth.currentUser!!.updateEmail(_email.value.text)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    Toast.makeText(application, "Update email", Toast.LENGTH_LONG).show()
                                                } else {
                                                    Toast.makeText(application, "Problme with update email", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                    } else {
                                        Toast.makeText(application, "Wrong password!", Toast.LENGTH_LONG).show()
                                    }
                                }
                        } else {
                            Toast.makeText(application, "Problem with database!", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(application, "Wrong email sentence!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(application, "Fill all fields!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}