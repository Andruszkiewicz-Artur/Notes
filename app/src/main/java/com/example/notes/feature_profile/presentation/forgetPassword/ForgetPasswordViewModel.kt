package com.example.notes.feature_profile.presentation.forgetPassword

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.feature_profile.domain.unit.decodeError
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val profileUseCases: ProfileUseCases
): ViewModel() {
    private val _state = MutableStateFlow(ForgetPasswordState())
    val state = _state.asStateFlow()

    fun onEvent(event: ForgetPasswordEvent) {
        when (event) {
            is ForgetPasswordEvent.EnteredEmail -> {
                _state.update { it.copy(
                    email = event.value
                ) }
            }
            is ForgetPasswordEvent.OnClickForgetPassword -> {
                if(isNoneError(event.context)) {
                    viewModelScope.launch {
                        val loginResult = profileUseCases.forgetPasswordUseCase.execute(
                            email = _state.value.email
                        )

                        if(!loginResult.successful) {
                            Toast.makeText(event.context, decodeError(loginResult.errorMessage, event.context), Toast.LENGTH_LONG).show()
                        } else {
                            viewModelScope.launch {
                                _state.update { it.copy(
                                    isSendMessage = true
                                ) }
                            }
                        }
                    }
                }
            }
        }
    }

    fun isNoneError(context: Context): Boolean {
        val email = validateUseCases.validateEmail.execute(_state.value.email)

        if (!email.successful) {
            _state.value = state.value.copy(
                errorEmail = decodeError(email.errorMessage, context)
            )
        }

        return email.successful
    }
}