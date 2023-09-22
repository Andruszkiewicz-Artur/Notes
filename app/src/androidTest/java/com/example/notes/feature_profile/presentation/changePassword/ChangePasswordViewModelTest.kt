package com.example.notes.feature_profile.presentation.changePassword

import androidx.test.core.app.ApplicationProvider
import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateEmail
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidatePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateRePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateTerms
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import com.example.notes.feature_profile.presentation.forgetPassword.ForgetPasswordViewModel
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class ChangePasswordViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var validateUseCases: ValidateUseCases
    private lateinit var profileUseCases: ProfileUseCases

    @Before
    fun setUp() {
        profileUseCases = mockk {
            coEvery { changePasswordUseCase.execute(any(), any()) } returns ValidationResult(true)
        }
        validateUseCases = ValidateUseCases(
            ValidateEmail(),
            ValidatePassword(),
            ValidateRePassword(),
            ValidateTerms()
        )

        viewModel = ChangePasswordViewModel(
            profileUseCases, validateUseCases
        )
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun enteredPassword_ContainsValue() {
        viewModel.onEvent(ChangePasswordEvent.EnteredOldPassword("artur"))
        assertThat(viewModel.state.value.oldPassword).contains("artur")
    }

    @Test
    fun enteredNewPassword_ContainsValue() {
        viewModel.onEvent(ChangePasswordEvent.EnteredNewPassword("artur"))
        assertThat(viewModel.state.value.newPassword).contains("artur")
    }

    @Test
    fun enteredNewRePassword_ContainsValue() {
        viewModel.onEvent(ChangePasswordEvent.EnteredRePassword("artur"))
        assertThat(viewModel.state.value.newRePassword).contains("artur")
    }

    @Test
    fun changePasswordVisibility_PresentPassword() {
        viewModel.onEvent(ChangePasswordEvent.ChangeVisibilityPassword)
        assertThat(viewModel.state.value.isPresentedPassword).isTrue()
    }

    @Test
    fun changePasswordVisibility_HidePassword() {
        viewModel.onEvent(ChangePasswordEvent.ChangeVisibilityPassword)
        viewModel.onEvent(ChangePasswordEvent.ChangeVisibilityPassword)
        assertThat(viewModel.state.value.isPresentedPassword).isFalse()
    }

    @Test
    fun changePasswordWithOutAnyPassword_IsErrorWithNewPassword() {
        viewModel.onEvent(ChangePasswordEvent.ResetPassword(ApplicationProvider.getApplicationContext()))

        assertThat(viewModel.state.value.errorNewPassword).isNotNull()
    }

    @Test
    fun changePasswordWithOutAnyPassword_IsErrorWithRePassword() {
        viewModel.onEvent(ChangePasswordEvent.EnteredNewPassword("arturartur1"))
        viewModel.onEvent(ChangePasswordEvent.ResetPassword(ApplicationProvider.getApplicationContext()))

        assertThat(viewModel.state.value.errorNewRePassword).isNotNull()
    }

    @Test
    fun changePassword_IsErrorWithRePassword() {
        viewModel.onEvent(ChangePasswordEvent.EnteredOldPassword("arturartur1"))
        viewModel.onEvent(ChangePasswordEvent.EnteredNewPassword("arturartur1"))
        viewModel.onEvent(ChangePasswordEvent.EnteredRePassword("arturartur1"))
        viewModel.onEvent(ChangePasswordEvent.ResetPassword(ApplicationProvider.getApplicationContext()))

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isPasswordChanged).isTrue()
    }
}