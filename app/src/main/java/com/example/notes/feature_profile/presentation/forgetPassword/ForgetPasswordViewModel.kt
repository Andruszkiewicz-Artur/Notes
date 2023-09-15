package com.example.notes.feature_profile.presentation.forgetPassword

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.R
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.feature_profile.domain.unit.decodeError
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val application: Application,
    private val validateUseCases: ValidateUseCases,
    private val profileUseCases: ProfileUseCases
): ViewModel() {
    private val _state = MutableStateFlow(ForgetPasswordState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEventForgetPassword>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: ForgetPasswordEvent) {
        when (event) {
            is ForgetPasswordEvent.EnteredEmail -> {
                _state.update { it.copy(
                    email = event.value
                ) }
            }
            is ForgetPasswordEvent.OnClickForgetPassword -> {
                if(isNoneError()) {
                    viewModelScope.launch {
                        val loginResult = profileUseCases.forgetPasswordUseCase.execute(
                            email = _state.value.email
                        )

                        if(!loginResult.successful) {
                            Toast.makeText(application, decodeError(loginResult.errorMessage, application), Toast.LENGTH_LONG).show()
                        } else {
                            viewModelScope.launch {
                                _eventFlow.emit(UiEventForgetPassword.ClickForgetPassword)
                            }
                        }
                    }
                }
            }
        }
    }

    fun isNoneError(): Boolean {
        val email = validateUseCases.validateEmail.execute(_state.value.email)

        if (!email.successful) {
            _state.value = state.value.copy(
                errorEmail = decodeError(email.errorMessage, application)
            )
        }

        return email.successful
    }
}