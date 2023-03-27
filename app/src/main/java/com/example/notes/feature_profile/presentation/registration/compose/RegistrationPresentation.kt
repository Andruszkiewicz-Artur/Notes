package com.example.notes.notes_future.presentation.register.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.compose.checkBox.CheckBox
import com.example.notes.feature_profile.presentation.registration.RegistrationEvent
import com.example.notes.feature_profile.presentation.registration.RegistrationViewModel
import com.example.notes.notes_future.present.addEditNote.compose.TextField

@Composable
fun RegistrationPresentation(
    navController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val email = viewModel.email.value
    val password = viewModel.password.value
    val rePassword = viewModel.rePassword.value
    val checkBox = viewModel.checkBox.value

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

            TextField(
                text = email.text,
                placeholder = email.placeholder,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.EnteredEmail(it))
                },
                onFocusChange = {
                    viewModel.onEvent(RegistrationEvent.ChangeFocusEmail(it))
                },
                singleLine = true,
                isPlaceholder = email.isPlaceholder,
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

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
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

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
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

            CheckBox(
                text = checkBox.text,
                checked = checkBox.isChacked,
                onCheckedChange = {
                    viewModel.onEvent(RegistrationEvent.CheckBox(it))
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.onEvent(RegistrationEvent.OnClickRegistration)
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(text = "Register")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "You have accoutn?")

                Text(
                    text = " Log in!",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
        }
    }
}