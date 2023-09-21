package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidateUseCasesTest {

    private lateinit var useCases: ValidateUseCases

    @Before
    fun setUp() {
        useCases = ValidateUseCases(
            ValidateEmail(),
            ValidatePassword(),
            ValidateRePassword(),
            ValidateTerms()
        )
    }

    //Testing Email
    @Test
    fun checkEmailWithOutDot_ResultIsFalse() = runBlocking {
        val email = "artur@gmailcom"
        val result = useCases.validateEmail.execute(email)

        assertThat(result.successful).isFalse()
    }

    @Test
    fun checkEmailWithOutAt_ResultIsFalse() = runBlocking {
        val email = "arturgmail.com"
        val result = useCases.validateEmail.execute(email)

        assertThat(result.successful).isFalse()
    }

    @Test
    fun checkEmailWithOutAtAndDote_ResultIsFalse() = runBlocking {
        val email = "arturgmailcom"
        val result = useCases.validateEmail.execute(email)

        assertThat(result.successful).isFalse()
    }

    @Test
    fun checkCorrectEmail_ResultIsTrue() = runBlocking {
        val email = "artur@gmail.com"
        val result = useCases.validateEmail.execute(email)

        assertThat(result.successful).isTrue()
    }

    //Testing Password
    @Test
    fun checkPasswordToShort_ResultIsFalse() = runBlocking {
        val password = "artur"
        val result = useCases.validatePassword.execute(password)

        assertThat(result.successful).isFalse()
    }

    @Test
    fun checkPasswordWithoutNumber_ResultIsFalse() = runBlocking {
        val password = "arturartur"
        val result = useCases.validatePassword.execute(password)

        assertThat(result.successful).isFalse()
    }

    @Test
    fun checkPasswordToShort_ResultIsTrue() = runBlocking {
        val password = "arturartur1"
        val result = useCases.validatePassword.execute(password)

        assertThat(result.successful).isTrue()
    }

    //Testing rePassword
    @Test
    fun checkDifferencePasswordsInRePassword_ResultIsFalse() = runBlocking {
        val password = "arturartur"
        val rePassword = "arturartur1"
        val result = useCases.validateRePassword.execute(password, rePassword)

        assertThat(result.successful).isFalse()
    }

    //Testing Terms
    @Test
    fun termIsChecked_ResultIsTrue() = runBlocking {
        val isAccepted = true
        val result = useCases.validateTerms.execute(isAccepted)

        assertThat(result.successful).isTrue()
    }

}