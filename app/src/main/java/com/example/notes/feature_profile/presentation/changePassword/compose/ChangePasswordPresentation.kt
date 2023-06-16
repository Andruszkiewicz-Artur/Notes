package com.example.notes.feature_profile.presentation.changePassword.compose

import androidx.compose.foundation.layout.*
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
import com.example.notes.feature_profile.presentation.changePassword.ChangePasswordEvent
import com.example.notes.feature_profile.presentation.changePassword.ChangePasswordViewModel
import com.example.notes.feature_profile.presentation.changePassword.UiEventChangePassword
import com.example.notes.feature_profile.presentation.unit.presentation.ValidateText
import kotlinx.coroutines.flow.collectLatest
import com.example.notes.R

@Composable
fun ChangePasswordPresentation(
    navController: NavHostController,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) { 
    val oldPasswordState = viewModel.oldPassword.value
    val newPasswordState = viewModel.newPassword.value
    val rePasswordState = viewModel.rePassword.value
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEventChangePassword.ChangePassword -> {
                    navController.popBackStack()
                }
            }
        }
    }
    
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

            TextFieldBordered(
                text = oldPasswordState.text,
                placeholder = oldPasswordState.placeholder,
                onValueChange = {
                    viewModel.onEvent(ChangePasswordEvent.EnteredOldPassword(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ChangePasswordEvent.ChangeOldPasswordFocus(it))
                },
                isPlaceholder = oldPasswordState.isPlaceholder,
                isSecure = true
            )
            
            Spacer(modifier = Modifier.height(40.dp))

            TextFieldBordered(
                text = newPasswordState.text,
                placeholder = newPasswordState.placeholder,
                onValueChange = {
                    viewModel.onEvent(ChangePasswordEvent.EnteredNewPassword(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ChangePasswordEvent.ChangeNewPasswordFocus(it))
                },
                isPlaceholder = newPasswordState.isPlaceholder,
                isSecure = true
            )

            ValidateText(
                text = state.errorNewPassword
            )

            TextFieldBordered(
                text = rePasswordState.text,
                placeholder = rePasswordState.placeholder,
                onValueChange = {
                    viewModel.onEvent(ChangePasswordEvent.EnteredRePassword(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ChangePasswordEvent.ChangeRePasswordFocus(it))
                },
                isPlaceholder = rePasswordState.isPlaceholder,
                isSecure = true
            )

            ValidateText(
                text = state.errorNewRePassword,
                spaceModifier = Modifier
                    .height(40.dp)
            )

            StandardButton(
                text = stringResource(id = R.string.ChangePassword)
            ) {
                viewModel.onEvent(ChangePasswordEvent.ResetPassword)
            }
        }
    }
}