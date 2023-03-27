package com.example.notes.core.util.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.notes.feature_profile.presentation.changeEmail.compose.ChangeEmailPresentation
import com.example.notes.feature_profile.presentation.changePassword.compose.ChangePasswordPresentation
import com.example.notes.notes_future.presentation.profile.compose.ProfilePresentation
import com.example.notes.notes_future.presentation.util.graph.loginNavGraph

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.PROFILE,
        startDestination = Screen.Profile.route
    ) {
        composable(
            route = Screen.Profile.route
        ) {
            ProfilePresentation(
                navController = navController
            )
        }

        composable(
            route = Screen.ChangeEmail.route
        ) {
            ChangeEmailPresentation(
                navController = navController
            )
        }

        composable(
            route = Screen.ChangePassword.route
        ) {
            ChangePasswordPresentation(
                navController = navController
            )
        }
    }

    loginNavGraph(navController)
}