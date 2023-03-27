package com.example.notes.core.util.graph

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

    object Login: Screen(
        route = "login_screen"
    )

    object Register: Screen(
        route = "register_screen"
    )

    object ForgetPassword: Screen(
        route = "forgetPassword_screen"
    )

    object ChangePassword: Screen(
        route = "changePassword_screen"
    )

    object ChangeEmail: Screen(
        route = "changeEmial_screen"
    )
}
