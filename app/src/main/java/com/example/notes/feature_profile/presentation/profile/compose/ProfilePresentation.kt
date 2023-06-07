package com.example.notes.notes_future.presentation.profile.compose

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.compose.button.ListButton
import com.example.notes.core.compose.button.StandardButton
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_profile.presentation.profile.ProfileEvent
import com.example.notes.feature_profile.presentation.profile.ProfileViewModel
import com.example.notes.feature_profile.presentation.profile.UiEventProfile
import com.example.notes.feature_profile.presentation.registration.RegistrationEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfilePresentation(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        if(!state.value.isUser) {
            navController.navigate(Screen.Login.route)
        }

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEventProfile.LogOut -> {
                    navController.navigate(Screen.Login.route)
                }
            }
        }
    }

    if (state.value.isUser) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = state.value.email,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(40.dp))

                ListButton(
                    text = "Change Email",
                    onClick = {
                        navController.navigate(Screen.ChangeEmail.route)
                    }
                )

                ListButton(
                    text = "Change Password",
                    onClick = {
                        navController.navigate(Screen.ChangePassword.route)
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Synchronize data"
                    )

                    Switch(
                        checked = state.value.isSynchronized,
                        onCheckedChange = {
                            viewModel.onEvent(ProfileEvent.saveDataInCloud(it))
                        }
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                StandardButton(
                    text = "Log out"
                ) {
                    viewModel.onEvent(ProfileEvent.LogOut)
                }
            }
        }
    }
}