package com.example.notes.core.util.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

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