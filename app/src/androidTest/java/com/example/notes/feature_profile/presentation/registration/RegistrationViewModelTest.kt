package com.example.notes.feature_profile.presentation.registration

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RegistrationViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var profileUseCases: ProfileUseCases
    private lateinit var useCases: ValidateUseCases

    @Before
    fun setUp() {
        profileUseCases = mockk {
            coEvery { registrationUseCase.execute(any(), any()) } returns ValidationResult(true)
        }
        useCases = ValidateUseCases(
            ValidateEmail(),
            ValidatePassword(),
            ValidateRePassword(),
            ValidateTerms()
        )

        viewModel = RegistrationViewModel(
            useCases,
            profileUseCases
        )
        Dispatchers.setMain(dispatcher)

    }

    @Test
    fun addingEmailValue_StateContainsValue() {
        viewModel.onEvent(RegistrationEvent.EnteredEmail("artur"))
        assertThat(viewModel.state.value.email).contains("artur")
    }

    @Test
    fun addingPasswordValue_StateContainsValue() {
        viewModel.onEvent(RegistrationEvent.EnteredPassword("artur"))
        assertThat(viewModel.state.value.password).contains("artur")
    }

    @Test
    fun addingRePasswordValue_StateContainsValue() {
        viewModel.onEvent(RegistrationEvent.EnteredRePassword("artur"))
        assertThat(viewModel.state.value.rePassword).contains("artur")
    }

    @Test
    fun clickBox_StateChangeValueToTrue() {
        viewModel.onEvent(RegistrationEvent.CheckBox(true))
        assertThat(viewModel.state.value.isTerms).isTrue()
    }

    @Test
    fun clickPasswordVisibility_StateChangeValueToTrue() {
        viewModel.onEvent(RegistrationEvent.OnClickChangeVisibilityPassword)
        assertThat(viewModel.state.value.isPresentedPassword).isTrue()
    }

    @Test
    fun doubleClickPasswordVisibility_StateChangeValueToTrue() {
        viewModel.onEvent(RegistrationEvent.OnClickChangeVisibilityPassword)
        viewModel.onEvent(RegistrationEvent.OnClickChangeVisibilityPassword)
        assertThat(viewModel.state.value.isPresentedPassword).isFalse()
    }

    @Test
    fun clickRegistrationWithAllFieldsEmpty_HasErrors() = runBlocking {
        viewModel.onEvent(RegistrationEvent.OnClickRegistration(ApplicationProvider.getApplicationContext()))
        dispatcher.scheduler.advanceUntilIdle()
        val allErrorsCount = listOf(
            viewModel.state.value.errorEmail,
            viewModel.state.value.errorPassword,
            viewModel.state.value.errorTerms
        ).filterNotNull()
            .size

        assertThat(allErrorsCount).isEqualTo(3)
    }

    @Test
    fun clickRegistrationWithIncorrectRePassword_HasError() = runBlocking {
        viewModel.onEvent(RegistrationEvent.EnteredEmail("artur@gmail.com"))
        viewModel.onEvent(RegistrationEvent.EnteredPassword("arturartur1"))
        viewModel.onEvent(RegistrationEvent.EnteredRePassword("Artur"))
        viewModel.onEvent(RegistrationEvent.CheckBox(true))
        viewModel.onEvent(RegistrationEvent.OnClickRegistration(ApplicationProvider.getApplicationContext()))
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.errorRePassword).isNotNull()
    }

    @Test
    fun clickRegistrationWithAllCorectData_SendDataViaEventFlow() = runBlocking {
        viewModel.onEvent(RegistrationEvent.EnteredEmail("artur@gmail.com"))
        viewModel.onEvent(RegistrationEvent.EnteredPassword("arturartur1"))
        viewModel.onEvent(RegistrationEvent.EnteredRePassword("arturartur1"))
        viewModel.onEvent(RegistrationEvent.CheckBox(true))
        viewModel.onEvent(RegistrationEvent.OnClickRegistration(ApplicationProvider.getApplicationContext()))
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isRegistered).isTrue()
    }
}