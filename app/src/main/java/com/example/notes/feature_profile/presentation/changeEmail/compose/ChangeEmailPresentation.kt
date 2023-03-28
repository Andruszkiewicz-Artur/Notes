package com.example.notes.feature_profile.presentation.changeEmail.compose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.compose.button.StandardButton
import com.example.notes.core.compose.textField.SecureTextField
import com.example.notes.core.compose.textField.TextFieldWithBorder
import com.example.notes.feature_profile.presentation.changeEmail.ChangeEmailEvent
import com.example.notes.feature_profile.presentation.changeEmail.ChangeEmailViewModel

@Composable
fun ChangeEmailPresentation(
    navController: NavHostController,
    viewModel: ChangeEmailViewModel = hiltViewModel()
) {
    val passwordState = viewModel.password.value
    val emailState = viewModel.email.value

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            SecureTextField(
                text = passwordState.text,
                placeholder = passwordState.placeholder,
                onValueChange = {
                    viewModel.onEvent(ChangeEmailEvent.EnteredPassword(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ChangeEmailEvent.ChangePasswordFocus(it))
                },
                isPlaceholder = passwordState.isPlaceholder
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextFieldWithBorder(
                text = emailState.text,
                placeholder = emailState.placeholder,
                onValueChange = {
                    viewModel.onEvent(ChangeEmailEvent.EnteredEmail(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ChangeEmailEvent.ChangeEmailFocus(it))
                },
                isPlaceholder = emailState.isPlaceholder
            )

            Spacer(modifier = Modifier.height(40.dp))

            StandardButton(
                text = "Change email"
            ) {
                viewModel.onEvent(ChangeEmailEvent.ChangeEmail)
            }
        }
    }
}