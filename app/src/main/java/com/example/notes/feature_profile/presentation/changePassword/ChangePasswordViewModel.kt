package com.example.notes.feature_profile.presentation.changePassword

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.feature_notes.presentation.auth
import com.google.firebase.auth.EmailAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val application: Application
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
                if (_oldPassword.value.text.isEmpty() && _newPassword.value.text.isEmpty() && _rePassword.value.text.isEmpty()) {
                    if(_newPassword.value.text == _rePassword.value.text) {
                        if(_newPassword.value.text.length >= 8) {
                            if (auth.currentUser != null) {
                                val user = auth.currentUser!!

                                val credential = EmailAuthProvider
                                    .getCredential(user.email!!, _oldPassword.value.text)

                                user.reauthenticate(credential)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            user.updatePassword(_newPassword.value.text)
                                                .addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        Toast.makeText(application, "Change password!", Toast.LENGTH_LONG).show()
                                                    } else {
                                                        Toast.makeText(application, "Problem with database!", Toast.LENGTH_LONG).show()
                                                    }
                                                }
                                        } else {
                                            Toast.makeText(application, "Old password is wrong!", Toast.LENGTH_LONG).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(application, "Problem with Database!", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(application, "Password is to short!", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(application, "Passwords are not the same!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(application, "Fill all fields!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}