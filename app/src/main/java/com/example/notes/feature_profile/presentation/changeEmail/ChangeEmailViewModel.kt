package com.example.notes.feature_profile.presentation.changeEmail

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
class ChangeEmailViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val validateUseCases: ValidateUseCases
): ViewModel() {
    private val _state = MutableStateFlow(ChangeEmailState())
    val state = _state.asStateFlow()

    fun onEvent(event: ChangeEmailEvent) {
        when (event) {
            is ChangeEmailEvent.EnteredEmail -> {
                _state.update { it.copy(
                    email = event.value
                ) }
            }
            is ChangeEmailEvent.EnteredPassword -> {
                _state.update { it.copy(
                    password = event.value
                ) }
            }
            is ChangeEmailEvent.ChangeEmail -> {
                if (isNoneError(event.context)) {
                    viewModelScope.launch {
                        val changeEmailResult = profileUseCases.changeEmailUseCase.execute(
                            newEmail = _state.value.email,
                            password = _state.value.password
                        )

                        if(!changeEmailResult.successful) {
                            Toast.makeText(event.context, decodeError(changeEmailResult.errorMessage, event.context), Toast.LENGTH_LONG).show()
                        } else {
                            _state.update { it.copy(
                                isEmailChanged = true
                            ) }
                        }
                    }
                }
            }
            ChangeEmailEvent.ChangePasswordPresentation -> {
                _state.update { it.copy(
                    isPresentedPassword = it.isPresentedPassword.not()
                ) }
            }
        }
    }

    private fun isNoneError(context: Context): Boolean {
        val password = validateUseCases.validatePassword.execute(_state.value.password)
        val email = validateUseCases.validateEmail.execute(_state.value.email)

        val hasError = !email.successful && !password.successful

        if (hasError) {
            _state.value = state.value.copy(
                errorPassword = decodeError(password.errorMessage, context),
                errorEmail = decodeError(email.errorMessage, context)
            )
        }

        return !hasError
    }
}