package com.example.notes.feature_notes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notes.core.compose.BottomBar
import com.example.notes.core.util.graph.RootNavGraph
import com.example.notes.ui.theme.NotesTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

lateinit var auth: FirebaseAuth

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navHostController: NavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        setContent {
            navHostController = rememberNavController()

            NotesTheme {
                Scaffold(
                    bottomBar = {
                        BottomBar(navHostController = navHostController)
                    }
                ) {
      \             RootNavGraph(
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}