package com.example.notes.core.compose.BottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.notes.R

sealed class BottomBarScreen(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    object Home: BottomBarScreen(
        route = "notes_screen",
        title = R.string.notes,
        icon = Icons.Filled.Create
    )

    object Profile: BottomBarScreen(
        route = "profile_screen",
        title = R.string.Profile,
        icon = Icons.Filled.Person
    )
}
