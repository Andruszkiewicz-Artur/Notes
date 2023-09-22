package com.example.notes.feature_profile.presentation.changePassword.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.feature_profile.presentation.changePassword.ChangePasswordEvent
import com.example.notes.feature_profile.presentation.changePassword.ChangePasswordViewModel
import com.example.notes.feature_profile.presentation.changePassword.UiEventChangePassword
import kotlinx.coroutines.flow.collectLatest
import com.example.notes.R
import com.example.notes.feature_profile.unit.comp.TextField

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChangePasswordPresentation(
    navController: NavHostController,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEventChangePassword.ChangePassword -> {
                    navController.popBackStack()
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.ChangePassword))
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
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(it)
            ) {
                TextField(
                    value = state.oldPassword,
                    onValueChange = {
                        viewModel.onEvent(ChangePasswordEvent.EnteredOldPassword(it))
                    },
                    label = stringResource(id = R.string.OldPassword),
                    leftIcon = Icons.Rounded.Lock,
                    isPassword = true,
                    showPassword = state.isPresentedPassword,
                    clickVisibilityPassword = {
                        viewModel.onEvent(ChangePasswordEvent.ChangeVisibilityPassword)
                    },
                    imeAction = ImeAction.Next,
                    onNext = {
                        focusRequester.requestFocus()
                    },
                    keyboardType = KeyboardType.Password,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = state.newPassword,
                    onValueChange = {
                        viewModel.onEvent(ChangePasswordEvent.EnteredNewPassword(it))
                    },
                    label = stringResource(id = R.string.NewPassword),
                    leftIcon = Icons.Rounded.Lock,
                    isPassword = true,
                    showPassword = state.isPresentedPassword,
                    clickVisibilityPassword = {
                        viewModel.onEvent(ChangePasswordEvent.ChangeVisibilityPassword)
                    },
                    imeAction = ImeAction.Next,
                    onNext = {
                        focusRequester.requestFocus()
                    },
                    keyboardType = KeyboardType.Password,
                    errorMessage = state.errorNewPassword,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                )

                TextField(
                    value = state.newRePassword,
                    onValueChange = {
                        viewModel.onEvent(ChangePasswordEvent.EnteredRePassword(it))
                    },
                    label = stringResource(id = R.string.Password),
                    leftIcon = Icons.Rounded.Lock,
                    isPassword = true,
                    showPassword = state.isPresentedPassword,
                    clickVisibilityPassword = {
                        viewModel.onEvent(ChangePasswordEvent.ChangeVisibilityPassword)
                    },
                    imeAction = ImeAction.Done,
                    onDone = {
                        keyboardController?.hide()
                    },
                    keyboardType = KeyboardType.Password,
                    errorMessage = state.errorNewRePassword,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.onEvent(ChangePasswordEvent.ResetPassword(context)) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.ChangePassword))
                }
            }
        }
    }
}