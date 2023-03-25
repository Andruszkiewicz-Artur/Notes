package com.example.notes.notes_future.presentation.forgotPassword.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.feature_profile.presentation.forgetPassword.ForgetPasswordEvent
import com.example.notes.feature_profile.presentation.forgetPassword.ForgetPasswordViewModel
import com.example.notes.notes_future.present.addEditNote.compose.TextField

@Composable
fun ForgetPasswordPresentation(
    navController: NavHostController,
    viewModel: ForgetPasswordViewModel = hiltViewModel()
) {
    val emailState = viewModel.email.value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            Text(
                text = "If you forget password, send the message to reset password on your email.",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.padding(top = 40.dp))

            TextField(
                text = emailState.text,
                placeholder = emailState.placeholder,
                onValueChange = {
                    viewModel.onEvent(ForgetPasswordEvent.EnteredEmail(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ForgetPasswordEvent.ChangeEmailFocus(it))
                },
                isPlaceholder = emailState.isPlaceholder,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .border(
                        color = MaterialTheme.colorScheme.primary,
                        width = 2.dp,
                        shape = CircleShape
                    )
                    .padding(horizontal = 10.dp)
            )

            Button(
                onClick = {
                    viewModel.onEvent(ForgetPasswordEvent.OnClickForgetPassword)
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                androidx.compose.material3.Text(text = "Send message")
            }
        }
    }
}