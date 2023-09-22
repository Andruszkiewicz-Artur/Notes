package com.example.notes.notes_future.presentation.login.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_profile.presentation.login.LoginEvent
import com.example.notes.feature_profile.presentation.login.LoginEvent.ClickLogin
import com.example.notes.feature_profile.presentation.login.LoginViewModel
import com.example.notes.feature_profile.presentation.login.UiEventLogin
import kotlinx.coroutines.flow.collectLatest
import com.example.notes.R
import com.example.notes.feature_profile.unit.comp.TextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPresentation(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

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
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = stringResource(id = R.string.WelcomeBack),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            TextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredLogin(it))
                },
                label = stringResource(id = R.string.Email),
                leftIcon = Icons.Rounded.Email,
                imeAction = ImeAction.Next,
                onNext = {
                    focusRequester.freeFocus()
                },
                keyboardType = KeyboardType.Email,
                errorMessage = state.emailErrorMessage,
                modifier = Modifier
                    .focusRequester(focusRequester)
            )

            TextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                label = stringResource(id = R.string.Password),
                leftIcon = Icons.Rounded.Lock,
                isPassword = true,
                showPassword = state.isPasswordPresented,
                clickVisibilityPassword = {
                    viewModel.onEvent(LoginEvent.ChangeVisibilityPassword)
                },
                imeAction = ImeAction.Done,
                onDone = {
                    keyboardController?.hide()
                },
                keyboardType = KeyboardType.Password,
                errorMessage = state.passwordErrorMessage,
                modifier = Modifier
                    .focusRequester(focusRequester)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.ForgetPassword),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Screen.ForgetPassword.route)
                        }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { viewModel.onEvent(ClickLogin(context)) },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.Login))
            }
            
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.YouDontHaveAnAccount),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = " " + stringResource(id = R.string.Register) + "!",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                            navController.navigate(Screen.Register.route)
                        }
                )
            }
        }
    }
}