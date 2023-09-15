package com.example.notes.feature_profile.presentation.registration

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.R
import com.example.notes.core.compose.checkBox.CheckBoxState
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.core.model.ProfileModel
import com.example.notes.core.value.Static
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
class RegistrationViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val application: Application,
    private val profileUseCases: ProfileUseCases
): ViewModel() {

    companion object {
        private const val TAG = "RegistrationViewModel_TAG"
    }

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEventRegistration>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.EnteredEmail -> {
                _state.update { it.copy(
                    email = event.value
                ) }
            }
            is RegistrationEvent.EnteredPassword -> {
                _state.update { it.copy(
                    password = event.value
                ) }
            }
            is RegistrationEvent.EnteredRePassword -> {
                _state.update { it.copy(
                    rePassword = event.value
                ) }
            }
            is RegistrationEvent.CheckBox -> {
                _state.update { it.copy(
                    isTerms = event.checkBox
                ) }
            }
            is RegistrationEvent.OnClickRegistration -> {
                viewModelScope.launch {
                    if (isNoneErrors()) {
                        val registrationResult = profileUseCases.registrationUseCase.execute(
                            email = _state.value.email,
                            password = _state.value.password
                        )

                        Log.d(TAG, "$registrationResult")

                        if(!registrationResult.successful) {
                            Toast.makeText(application, decodeError(registrationResult.errorMessage, application), Toast.LENGTH_LONG).show()
                        } else {
                            Static.profileSetting = ProfileModel()
                            viewModelScope.launch {
                                _eventFlow.emit(UiEventRegistration.Register)
                            }
                        }
                    }
                }
            }
            RegistrationEvent.OnClickChangeVisibilityPassword -> {
                _state.update { it.copy(
                    isPresentedPassword = it.isPresentedPassword.not()
                ) }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val email = validateUseCases.validateEmail.execute(_state.value.email)
        val password = validateUseCases.validatePassword.execute(_state.value.password)
        val rePassword = validateUseCases.validateRePassword.execute(_state.value.password, _state.value.rePassword)
        val terms = validateUseCases.validateTerms.execute(_state.value.isTerms)

        val hasError = listOf(
            email,
            password,
            rePassword,
            terms
        ).any { !it.successful }

        if (hasError) {
            _state.value = state.value.copy(
                errorEmail = decodeError(email.errorMessage, application),
                errorPassword = decodeError(password.errorMessage, application),
                errorRePassword = decodeError(rePassword.errorMessage, application),
                errorTerms = decodeError(terms.errorMessage, application)
            )
        }

        Log.d("has error", hasError.toString())

        return !hasError
    }
}