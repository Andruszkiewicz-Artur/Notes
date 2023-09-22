package com.example.notes.feature_profile.presentation.login

import androidx.test.core.app.ApplicationProvider
import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateEmail
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidatePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateRePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateTerms
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.security.Provider

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class LoginViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: LoginViewModel
    private lateinit var profileUseCases: ProfileUseCases
    private lateinit var validateUseCases: ValidateUseCases

    @Before
    fun setUp() {
        profileUseCases = mockk {
            coEvery { logInUseCase.execute(any(), any()) } returns ValidationResult(true)
        }

        validateUseCases = ValidateUseCases(
            ValidateEmail(),
            ValidatePassword(),
            ValidateRePassword(),
            ValidateTerms()
        )

        viewModel = LoginViewModel(
            validateUseCases,
            profileUseCases
        )
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun addEmailToState_ContainsValue() {
        viewModel.onEvent(LoginEvent.EnteredLogin("artur"))
        assertThat(viewModel.state.value.email).contains("artur")
    }

    @Test
    fun addPaswordToState_ContainsValue() {
        viewModel.onEvent(LoginEvent.EnteredPassword("artur"))
        assertThat(viewModel.state.value.password).contains("artur")
    }

    @Test
    fun changePasswordVisibility_ShowPassword() {
        viewModel.onEvent(LoginEvent.ChangeVisibilityPassword)

        assertThat(viewModel.state.value.isPasswordPresented).isTrue()
    }

    @Test
    fun doubleChangePasswordVisibility_DontShowPassword() {
        viewModel.onEvent(LoginEvent.ChangeVisibilityPassword)
        viewModel.onEvent(LoginEvent.ChangeVisibilityPassword)

        assertThat(viewModel.state.value.isPasswordPresented).isFalse()
    }

    @Test
    fun loginWithOutLoginAndPassword_ContainsTwoErrors() {
        viewModel.onEvent(LoginEvent.ClickLogin(ApplicationProvider.getApplicationContext()))
        val countOfErrors = listOf(
            viewModel.state.value.emailErrorMessage,
            viewModel.state.value.passwordErrorMessage
        ).filterNotNull().size

        assertThat(countOfErrors).isEqualTo(2)
    }

    @Test
    fun loginWithPasswordAndEmail_ResultIsTrue() {
        viewModel.onEvent(LoginEvent.EnteredLogin("artur@gmail.com"))
        viewModel.onEvent(LoginEvent.EnteredLogin("arturartur1"))
        viewModel.onEvent(LoginEvent.ClickLogin(ApplicationProvider.getApplicationContext()))

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isLogIn).isFalse()
    }
}