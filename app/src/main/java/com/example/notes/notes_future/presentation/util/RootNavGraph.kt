package com.example.notes.notes_future.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.notes.notes_future.presentation.util.notes.notesNavGraph
import com.example.notes.notes_future.presentation.util.profile.profileNavGraph

@Composable
fun RootNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.NOTES
    ) {
        notesNavGraph(navHostController)
        profileNavGraph(navHostController)
    }
}