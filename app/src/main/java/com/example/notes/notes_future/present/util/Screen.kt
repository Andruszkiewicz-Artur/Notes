package com.example.notes.notes_future.present.util

sealed class Screen(
    val route: String
) {
    object Home: Screen(
        route = "home"
    )

    object AddEdit: Screen(
        route = "addEdit"
    ) {
        fun sendNoteId(
            noteId: Int
        ): String {
            return this.route + "?noteId=$noteId"
        }
    }
}
