package com.example.notes.feature_profile.presentation.changeEmail.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.notes.feature_profile.presentation.login.LoginEvent
import com.example.notes.feature_profile.unit.comp.TextField

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChangeEmailPresentation(
    navController: NavHostController,
    viewModel: ChangeEmailViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEventChangeEmail.ChangeEmail -> {
                    navController.popBackStack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.ChangeEmail))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {

                TextField(
                    value = state.email,
                    onValueChange = {
                        viewModel.onEvent(ChangeEmailEvent.EnteredEmail(it))
                    },
                    label = stringResource(id = R.string.Email),
                    leftIcon = Icons.Rounded.Email,
                    imeAction = ImeAction.Next,
                    onNext = {
                        focusRequester.freeFocus()
                    },
                    keyboardType = KeyboardType.Email,
                    errorMessage = state.errorEmail,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                )

                TextField(
                    value = state.password,
                    onValueChange = {
                        viewModel.onEvent(ChangeEmailEvent.EnteredPassword(it))
                    },
                    label = stringResource(id = R.string.Password),
                    leftIcon = Icons.Rounded.Lock,
                    isPassword = true,
                    showPassword = state.isPresentedPassword,
                    clickVisibilityPassword = {
                        viewModel.onEvent(ChangeEmailEvent.ChangePasswordPresentation)
                    },
                    imeAction = ImeAction.Done,
                    onDone = {
                        keyboardController?.hide()
                    },
                    keyboardType = KeyboardType.Password,
                    errorMessage = state.errorPassword,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.onEvent(ChangeEmailEvent.ChangeEmail(context)) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.ChangeEmail))
                }
            }
        }
    }
}