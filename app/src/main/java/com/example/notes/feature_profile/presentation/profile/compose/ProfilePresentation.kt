package com.example.notes.notes_future.presentation.profile.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_profile.presentation.profile.ProfileViewModel

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
    }

}