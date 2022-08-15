package com.example.notes.notes_future.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notes.ui.theme.NotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navHostController: NavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navHostController = rememberNavController()

            NotesTheme {
                HomePresent()
            }
        }
    }
}