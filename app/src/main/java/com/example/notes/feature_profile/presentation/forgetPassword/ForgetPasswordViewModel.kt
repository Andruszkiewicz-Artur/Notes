package com.example.notes.feature_profile.presentation.forgetPassword

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.notes.feature_profile.presentation.profile.ProfileEvent
import com.example.notes.feature_profile.presentation.registration.RegistrationEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val application: Application
): ViewModel() {

    var state = mutableStateOf("")

    fun onEvent(event: ForgetPasswordEvent) {
        when (event) {
            is ForgetPasswordEvent.EnteredEmail -> {
                state.value = event.value
            }
            is ForgetPasswordEvent.OnClickForgetPassword -> {
                if(state.value.isNotBlank()) {
                    if(state.value.contains("@") && state.value.contains(".")) {
                        Firebase.auth.sendPasswordResetEmail(state.value)
                            .addOnCompleteListener { task ->
                                if(task.isSuccessful) {
                                    Toast.makeText(application, "Email sent!", Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(application, "Email is incorrect!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(application, "Fill field!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}