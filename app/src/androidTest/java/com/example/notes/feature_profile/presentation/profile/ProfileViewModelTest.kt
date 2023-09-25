package com.example.notes.feature_profile.presentation.profile

import com.example.notes.core.model.ProfileModel
import com.example.notes.core.value.Static
import com.example.notes.feature_notes.domain.unit.Resource
import com.example.notes.feature_notes.domain.use_case.remote.RemoteUseCases
import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
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
class ProfileViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ProfileViewModel
    private lateinit var remoteUseCases: RemoteUseCases
    private lateinit var profileUseCases: ProfileUseCases

    @Before
    fun setUp() {
        profileUseCases = mockk {
            coEvery { logOutUseCase.execute() } returns ValidationResult(true)
        }
        remoteUseCases = mockk {
            coEvery { setUpSynchronizeUseCase.execute(any()) } returns  ValidationResult(true)
            coEvery { checkIsSynchronize.execute() } returns Resource.Success(true)
        }

        viewModel = ProfileViewModel(
            remoteUseCases, profileUseCases
        )
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun changeSynchronizeToDontSynchronize_DontSynchronizeData() {
        viewModel.onEvent(ProfileEvent.saveDataInCloud(true))

        assertThat(viewModel.state.value.isSynchronized).isTrue()
    }

    @Test
    fun logOutFromApp_LogOut() {
        viewModel.onEvent(ProfileEvent.LogOut)

        assertThat(viewModel.state.value.isUser).isFalse()
    }

    @Test
    fun userNotLoginYet_UserCantFigureInApp() {
        viewModel.initFunc(null)

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isUser).isFalse()
        assertThat(viewModel.state.value.email).isEmpty()
        assertThat(Static.profileSetting).isNull()
    }

    @Test
    fun userLogin_UserFigureInApp() {
        viewModel.initFunc("artur@gmail.com")
        Static.profileSetting = ProfileModel()

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isUser).isTrue()
        assertThat(viewModel.state.value.email).isNotEmpty()
        assertThat(Static.profileSetting).isNotNull()
    }

}