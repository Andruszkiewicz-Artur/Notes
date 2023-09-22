package com.example.notes.feature_profile.presentation.changeEmail

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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class ChangeEmailViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ChangeEmailViewModel
    private lateinit var validateUseCases: ValidateUseCases
    private lateinit var profileUseCases: ProfileUseCases

    @Before
    fun setUp() {
        profileUseCases = mockk {
            coEvery { changeEmailUseCase.execute(any(), any()) } returns ValidationResult(true)
        }
        validateUseCases = ValidateUseCases(
            ValidateEmail(),
            ValidatePassword(),
            ValidateRePassword(),
            ValidateTerms()
        )

        viewModel = ChangeEmailViewModel(
            profileUseCases, validateUseCases
        )
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun enteredEmail_ContainsValueInState() {
        viewModel.onEvent(ChangeEmailEvent.EnteredEmail("artur"))

        assertThat(viewModel.state.value.email).contains("artur")
    }

    @Test
    fun enteredPassword_ContainsValueInState() {
        viewModel.onEvent(ChangeEmailEvent.EnteredPassword("artur"))

        assertThat(viewModel.state.value.password).contains("artur")
    }

    @Test
    fun clickVisibilityPassword_ShowPassword() {
        viewModel.onEvent(ChangeEmailEvent.ChangePasswordPresentation)

        assertThat(viewModel.state.value.isPresentedPassword).isTrue()
    }

    @Test
    fun doubleClickVisibilityPassword_HidePassword() {
        viewModel.onEvent(ChangeEmailEvent.ChangePasswordPresentation)
        viewModel.onEvent(ChangeEmailEvent.ChangePasswordPresentation)

        assertThat(viewModel.state.value.isPresentedPassword).isFalse()
    }

    @Test
    fun changeEmailWithOutEnteredData_ShowTwoErrors() {
        viewModel.onEvent(ChangeEmailEvent.ChangeEmail(ApplicationProvider.getApplicationContext()))

        val countOfErrors = listOfNotNull(
            viewModel.state.value.errorEmail,
            viewModel.state.value.errorPassword
        ).size

        assertThat(countOfErrors).isEqualTo(2)
    }

    @Test
    fun changeEmailWithEnteredData_ChangeEmail() {
        viewModel.onEvent(ChangeEmailEvent.EnteredEmail("artur@gmail.com"))
        viewModel.onEvent(ChangeEmailEvent.EnteredPassword("arturartur1"))

        viewModel.onEvent(ChangeEmailEvent.ChangeEmail(ApplicationProvider.getApplicationContext()))

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isEmailChanged).isTrue()
    }

}