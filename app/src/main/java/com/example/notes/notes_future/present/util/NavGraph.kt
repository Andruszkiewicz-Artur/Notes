package com.example.notes.notes_future.present.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notes.notes_future.present.addEditNote.compose.AddEditPresent
import com.example.notes.notes_future.present.notes.compose.NotesPresent

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
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