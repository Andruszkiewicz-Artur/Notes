package com.example.notes.notes_future.present.home.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notes.core.compose.BottomBar
import com.example.notes.notes_future.present.util.RootNavGraph

@ExperimentalMaterial3Api
@Composable
fun HomePresent(
    navHostController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomBar(navHostController = navHostController)
        }
    ) {
        RootNavGraph(
            navHostController = navHostController
        )
    }
}