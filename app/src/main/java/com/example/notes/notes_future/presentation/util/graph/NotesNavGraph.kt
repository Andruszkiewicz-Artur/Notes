package com.example.notes.notes_future.presentation.util.notes

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.notes.notes_future.presentation.addEditNote.compose.AddEditPresentation
import com.example.notes.notes_future.presentation.notes.compose.NotesPresentation
import com.example.notes.notes_future.presentation.util.Graph
import com.example.notes.notes_future.presentation.util.Screen

fun NavGraphBuilder.notesNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.NOTES,
        startDestination = Screen.Notes.route
    ) {
        composable(
            route = Screen.Notes.route
        ) {
            NotesPresentation(
                navHostController = navController
            )
        }

        composable(
            route = Screen.AddEdit.route + "?noteId={noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditPresentation(
                navHostController = navController
            )
        }
    }
}