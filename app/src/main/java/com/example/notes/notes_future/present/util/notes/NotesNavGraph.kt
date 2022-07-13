package com.example.notes.notes_future.present.util.notes

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.notes.notes_future.present.addEditNote.compose.AddEditPresent
import com.example.notes.notes_future.present.notes.compose.NotesPresent
import com.example.notes.notes_future.present.util.Graph
import com.example.notes.notes_future.present.util.Screen

fun NavGraphBuilder.notesNavGraph(
    navHostController: NavHostController
) {
    navigation(
        route = Graph.NOTES,
        startDestination = Screen.Notes.route
    ) {
        composable(
            route = Screen.Notes.route
        ) {
            NotesPresent(
                navHostController = navHostController
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
            AddEditPresent(
                navHostController = navHostController
            )
        }
    }
}