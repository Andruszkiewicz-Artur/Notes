package com.example.notes.notes_future.presentation.forgotPassword.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.compose.button.StandardButton
import com.example.notes.core.compose.textField.TextFieldBordered
import com.example.notes.feature_profile.presentation.forgetPassword.ForgetPasswordEvent
import com.example.notes.feature_profile.presentation.forgetPassword.ForgetPasswordViewModel
import com.example.notes.feature_profile.presentation.forgetPassword.UiEventForgetPassword
import com.example.notes.feature_profile.presentation.login.UiEventLogin
import com.example.notes.feature_profile.presentation.unit.presentation.ValidateText
import kotlinx.coroutines.flow.collectLatest
import com.example.notes.R

@Composable
fun ForgetPasswordPresentation(
    navController: NavHostController,
    viewModel: ForgetPasswordViewModel = hiltViewModel()
) {
    val emailState = viewModel.email.value
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEventForgetPassword.ClickForgetPassword -> {
                    navController.popBackStack()
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.ForgetPassword) + "?",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(id = R.string.ForgetPasswordInstuction),
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.padding(top = 40.dp))

            TextFieldBordered(
                text = emailState.text,
                placeholder = emailState.placeholder,
                onValueChange = {
                    viewModel.onEvent(ForgetPasswordEvent.EnteredEmail(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ForgetPasswordEvent.ChangeEmailFocus(it))
                },
                isPlaceholder = emailState.isPlaceholder,
                singleLine = true
            )

            ValidateText(
                text = state.errorEmail,
                spaceModifier = Modifier
                    .height(40.dp)
            )

            StandardButton(
                text = stringResource(id = R.string.SendMessage)
            ) {
                viewModel.onEvent(ForgetPasswordEvent.OnClickForgetPassword)
            }
        }
    }
}