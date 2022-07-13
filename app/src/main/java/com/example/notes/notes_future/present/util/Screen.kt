package com.example.notes.notes_future.present.util

sealed class Screen(
    val route: String
) {
    object Notes: Screen(
        route = "notes_screen"
    )

    object Profile: Screen(
        route = "profile_screen"
    )

    object AddEdit: Screen(
        route = "addEdit_screen"
    ) {
        fun sendNoteId(
            noteId: Int
        ): String {
            return this.route + "?noteId=$noteId"
        }
    }
}
