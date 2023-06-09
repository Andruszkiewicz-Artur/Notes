package com.example.notes.notes_future.presentation.register.compose

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.compose.button.StandardButton
import com.example.notes.core.compose.checkBox.CheckBox
import com.example.notes.core.compose.textField.TextFieldBordered
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_profile.presentation.login.UiEventLogin
import com.example.notes.feature_profile.presentation.registration.RegistrationEvent
import com.example.notes.feature_profile.presentation.registration.RegistrationViewModel
import com.example.notes.feature_profile.presentation.registration.UiEventRegistration
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegistrationPresentation(
    navController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val email = viewModel.email.value
    val password = viewModel.password.value
    val rePassword = viewModel.rePassword.value
    val checkBox = viewModel.checkBox.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEventRegistration.Register -> {
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
                    text = "Registration",
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = "Create your account",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            TextFieldBordered(
                text = email.text,
                placeholder = email.placeholder,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.EnteredEmail(it))
                },
                onFocusChange = {
                    viewModel.onEvent(RegistrationEvent.ChangeFocusEmail(it))
                },
                singleLine = true,
                isPlaceholder = email.isPlaceholder
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextFieldBordered(
                text = password.text,
                placeholder = password.placeholder,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.EnteredPassword(it))
                },
                onFocusChange = {
                    viewModel.onEvent(RegistrationEvent.ChangeFocusPassword(it))
                },
                singleLine = true,
                isPlaceholder = password.isPlaceholder,
                isSecure = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextFieldBordered(
                text = rePassword.text,
                placeholder = rePassword.placeholder,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.EnteredRePassword(it))
                },
                onFocusChange = {
                    viewModel.onEvent(RegistrationEvent.ChangeFocusRePassword(it))
                },
                singleLine = true,
                isPlaceholder = rePassword.isPlaceholder,
                isSecure = true
            )

            CheckBox(
                text = checkBox.text,
                checked = checkBox.isChacked,
                onCheckedChange = {
                    viewModel.onEvent(RegistrationEvent.CheckBox(it))
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            StandardButton(
                text = "Register"
            ) {
                viewModel.onEvent(RegistrationEvent.OnClickRegistration)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "You have account?")

                Text(
                    text = " Log in!",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                            navController.navigate(Screen.Login.route)
                        }
                )
            }
        }
    }
}