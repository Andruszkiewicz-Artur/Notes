package com.example.notes.notes_future.presentation.forgotPassword.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.feature_profile.presentation.forgetPassword.ForgetPasswordEvent
import com.example.notes.feature_profile.presentation.forgetPassword.ForgetPasswordViewModel
import com.example.notes.R
import com.example.notes.feature_profile.unit.comp.TextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgetPasswordPresentation(
    navController: NavHostController,
    viewModel: ForgetPasswordViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = state.isSendMessage) {
        if (state.isSendMessage) navController.popBackStack()
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
                text = stringResource(id = R.string.ForgetPassword),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.ForgetPasswordInstuction),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.padding(top = 40.dp))

            TextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(ForgetPasswordEvent.EnteredEmail(it))
                },
                label = stringResource(id = R.string.Email),
                leftIcon = Icons.Rounded.Email,
                keyboardType = KeyboardType.Email,
                errorMessage = state.errorEmail,
                imeAction = ImeAction.Done,
                onDone = {
                    keyboardController?.hide()
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { viewModel.onEvent(ForgetPasswordEvent.OnClickForgetPassword(context)) },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.SendMessage),
                    color = Color.White
                )
            }
        }
    }
}