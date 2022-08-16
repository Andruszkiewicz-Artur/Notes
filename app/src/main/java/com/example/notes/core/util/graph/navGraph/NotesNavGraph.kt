package com.example.notes.core.util.graph

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.notes.notes_future.presentation.addEditNote.compose.AddEditPresentation
import com.example.notes.notes_future.presentation.notes.compose.NotesPresentation

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