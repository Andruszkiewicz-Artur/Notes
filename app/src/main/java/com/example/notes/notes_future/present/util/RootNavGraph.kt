package com.example.notes.notes_future.present.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notes.notes_future.present.addEditNote.compose.AddEditPresent
import com.example.notes.notes_future.present.notes.compose.NotesPresent
import com.example.notes.notes_future.present.util.notes.notesNavGraph
import com.example.notes.notes_future.present.util.profile.profileNavGraph

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