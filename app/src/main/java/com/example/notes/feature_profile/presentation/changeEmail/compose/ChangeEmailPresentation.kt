package com.example.notes.feature_profile.presentation.changeEmail.compose

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
import com.example.notes.feature_profile.presentation.changeEmail.ChangeEmailEvent
import com.example.notes.feature_profile.presentation.changeEmail.ChangeEmailViewModel
import com.example.notes.feature_profile.presentation.changeEmail.UiEventChangeEmail
import com.example.notes.feature_profile.presentation.changePassword.UiEventChangePassword
import com.example.notes.feature_profile.presentation.unit.presentation.ValidateText
import kotlinx.coroutines.flow.collectLatest
import com.example.notes.R

@Composable
fun ChangeEmailPresentation(
    navController: NavHostController,
    viewModel: ChangeEmailViewModel = hiltViewModel()
) {
    val passwordState = viewModel.password.value
    val emailState = viewModel.email.value
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEventChangeEmail.ChangeEmail -> {
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

            Spacer(modifier = Modifier.height(20.dp))

            ValidateText(
                text = state.errorEmail,
                spaceModifier = Modifier
                    .height(40.dp)
            )

            StandardButton(
                text = stringResource(id = R.string.ChangeEmail)
            ) {
                viewModel.onEvent(ChangeEmailEvent.ChangeEmail)
            }
        }
    }
}