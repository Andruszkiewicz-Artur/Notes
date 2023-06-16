package com.example.notes.notes_future.presentation.profile.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.compose.button.ListButton
import com.example.notes.core.compose.button.StandardButton
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_profile.presentation.profile.ProfileEvent
import com.example.notes.feature_profile.presentation.profile.ProfileViewModel
import com.example.notes.feature_profile.presentation.profile.compose.LoginRegisterPresentation
import com.example.notes.R

@Composable
fun ProfilePresentation(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(key1 = true, key2 = auth.currentUser) {
        viewModel.initFunc()
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
                    text = stringResource(id = R.string.ChangeEmail),
                    onClick = {
                        navController.navigate(Screen.ChangeEmail.route)
                    }
                )

                ListButton(
                    text = stringResource(id = R.string.ChangePassword),
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
                        text = stringResource(id = R.string.SynchronizeData)
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
                    text = stringResource(id = R.string.LogOut)
                ) {
                    viewModel.onEvent(ProfileEvent.LogOut)
                }
            }
        }
    } else {
        LoginRegisterPresentation(
            navController = navController
        )
    }
}