package com.example.notes.feature_profile.presentation.forgetPassword

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
class ForgetPasswordViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ForgetPasswordViewModel
    private lateinit var validateUseCases: ValidateUseCases
    private lateinit var profileUseCases: ProfileUseCases

    @Before
    fun setUp() {
        profileUseCases = mockk {
            coEvery { forgetPasswordUseCase.execute(any()) } returns ValidationResult(true)
        }
        validateUseCases = ValidateUseCases(
            ValidateEmail(),
            ValidatePassword(),
            ValidateRePassword(),
            ValidateTerms()
        )

        viewModel = ForgetPasswordViewModel(
            validateUseCases, profileUseCases
        )
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun addEmailToState_ContainsValue() {
        viewModel.onEvent(ForgetPasswordEvent.EnteredEmail("artur"))
        assertThat(viewModel.state.value.email).contains("artur")
    }

    @Test
    fun sendMessageWithOutEmail_ShowError() {
        viewModel.onEvent(ForgetPasswordEvent.OnClickForgetPassword(ApplicationProvider.getApplicationContext()))
        assertThat(viewModel.state.value.errorEmail).isNotNull()
    }

    @Test
    fun sendMessageWithEmail_ResultIsTrue() {
        viewModel.onEvent(ForgetPasswordEvent.EnteredEmail("artur@gmail.com"))
        viewModel.onEvent(ForgetPasswordEvent.OnClickForgetPassword(ApplicationProvider.getApplicationContext()))

        dispatcher.scheduler.advanceUntilIdle()
        
        assertThat(viewModel.state.value.isSendMessage).isTrue()
    }

}