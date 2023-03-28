package com.example.notes.feature_profile.presentation.changePassword.compose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.compose.button.StandardButton
import com.example.notes.core.compose.textField.SecureTextField
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.core.compose.textField.TextFieldWithBorder
import com.example.notes.feature_profile.presentation.changePassword.ChangePasswordEvent
import com.example.notes.feature_profile.presentation.changePassword.ChangePasswordViewModel

@Composable
fun ChangePasswordPresentation(
    navController: NavHostController,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) { 
    val oldPasswordState = viewModel.oldPassword.value
    val newPasswordState = viewModel.newPassword.value
    val rePasswordState = viewModel.rePassword.value
    
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
                text = oldPasswordState.text,
                placeholder = oldPasswordState.placeholder,
                onValueChange = {
                    viewModel.onEvent(ChangePasswordEvent.EnteredOldPassword(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ChangePasswordEvent.ChangeOldPasswordFocus(it))
                },
                isPlaceholder = oldPasswordState.isPlaceholder
            )
            
            Spacer(modifier = Modifier.height(40.dp))

            SecureTextField(
                text = newPasswordState.text,
                placeholder = newPasswordState.placeholder,
                onValueChange = {
                    viewModel.onEvent(ChangePasswordEvent.EnteredNewPassword(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ChangePasswordEvent.ChangeNewPasswordFocus(it))
                },
                isPlaceholder = newPasswordState.isPlaceholder
            )

            Spacer(modifier = Modifier.height(20.dp))

            SecureTextField(
                text = rePasswordState.text,
                placeholder = rePasswordState.placeholder,
                onValueChange = {
                    viewModel.onEvent(ChangePasswordEvent.EnteredRePassword(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ChangePasswordEvent.ChangeRePasswordFocus(it))
                },
                isPlaceholder = rePasswordState.isPlaceholder
            )

            Spacer(modifier = Modifier.height(40.dp))

            StandardButton(
                text = "Change Password"
            ) {
                viewModel.onEvent(ChangePasswordEvent.ResetPassword)
            }
        }
    }
}