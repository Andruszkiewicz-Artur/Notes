package com.example.notes.notes_future.presentation.login.compose

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.compose.button.StandardButton
import com.example.notes.core.compose.textField.TextFieldBordered
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_profile.presentation.login.LoginEvent
import com.example.notes.feature_profile.presentation.login.LoginEvent.ClickLogin
import com.example.notes.feature_profile.presentation.login.LoginViewModel
import com.example.notes.feature_profile.presentation.login.UiEventLogin
import kotlinx.coroutines.flow.collectLatest
import com.example.notes.R

@Composable
fun LoginPresentation(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val emailState = viewModel.email.value
    val passwordState = viewModel.password.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEventLogin.LogIn -> {
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.Login),
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = stringResource(id = R.string.WelcomeBack),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            TextFieldBordered(
                text = emailState.text,
                placeholder = emailState.placeholder,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredLogin(it))
                },
                onFocusChange = {
                    viewModel.onEvent(LoginEvent.ChangeLoginFocus(it))
                },
                isPlaceholder = emailState.isPlaceholder,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldBordered(
                text = passwordState.text,
                placeholder = passwordState.placeholder,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                onFocusChange = {
                    viewModel.onEvent(LoginEvent.ChangePasswordFocus(it))
                },
                singleLine = true,
                isPlaceholder = passwordState.isPlaceholder,
                isSecure = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.ForgetPassword),
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Screen.ForgetPassword.route)
                        }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            StandardButton(
                text = stringResource(id = R.string.Login)
            ) {
                viewModel.onEvent(ClickLogin(navController))
            }
            
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.Or)
                )
            }

            Button(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Register.route)
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            ) {
                Text(text = stringResource(id = R.string.Register))
            }
        }
    }
}