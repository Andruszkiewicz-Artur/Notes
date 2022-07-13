package com.example.notes.notes_future.present.util.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.notes.notes_future.present.profile.compose.ProfilePresent
import com.example.notes.notes_future.present.util.Graph
import com.example.notes.notes_future.present.util.Screen

fun NavGraphBuilder.profileNavGraph(
    navHostController: NavHostController
) {
    navigation(
        route = Graph.PROFILE,
        startDestination = Screen.Profile.route
    ) {
        composable(
            route = Screen.Profile.route
        ) {
            ProfilePresent(
                navHostController = navHostController
            )
        }
    }
}