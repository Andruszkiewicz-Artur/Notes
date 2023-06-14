package com.example.notes.feature_profile.domain.use_case.profileUseCases

data class ProfileUseCases(
    val logInUseCase: LogInUseCase,
    val registrationUseCase: RegistrationUseCase,
    val forgetPasswordUseCase: ForgetPasswordUseCase,
    val changePasswordUseCase: ChangePasswordUseCase,
    val changeEmailUseCase: ChangeEmailUseCase,
    val logOutUseCase: LogOutUseCase
)
