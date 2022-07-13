package com.example.notes.core.compose.BottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: BottomBarScreen(
        route = "notes_screen",
        title = "Notes",
        icon = Icons.Filled.Create
    )

    object Profile: BottomBarScreen(
        route = "profile_screen",
        title = "Profile",
        icon = Icons.Filled.Person
    )
}
