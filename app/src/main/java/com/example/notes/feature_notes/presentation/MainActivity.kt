package com.example.notes.feature_notes.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.notes.core.compose.BottomBar
import com.example.notes.core.compose.BottomBar.BottomBarScreen
import com.example.notes.core.util.graph.RootNavGraph
import com.example.notes.core.util.graph.Screen
import com.example.notes.ui.theme.NotesTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

lateinit var auth: FirebaseAuth
lateinit var database: DatabaseReference
//val Context.dataStore by dataStore("app-settings.json", SettingSerializer)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navHostController: NavHostController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        setContent {
            navHostController = rememberNavController()

            val screens = listOf(
                BottomBarScreen.Home,
                BottomBarScreen.Profile
            )

            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val isBottomBar = screens.any { it.route == currentDestination?.route }

            NotesTheme {
                Scaffold(
                    bottomBar = {
                        if (isBottomBar) {
                            BottomBar(
                                navHostController = navHostController,
                                currentDestination = currentDestination,
                                screens = screens
                            )
                        }
                    },
                    floatingActionButton = {
                        if(isBottomBar) {
                            FloatingActionButton(
                                onClick = {
                                    navHostController.navigate(Screen.AddEdit.route)
                                },
                                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = CircleShape,
                                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
                                modifier = Modifier
                                    .size(80.dp)
                                    .offset(
                                        y = 60.dp
                                    )
                                    .border(
                                        width = 10.dp,
                                        color = MaterialTheme.colorScheme.background,
                                        shape = CircleShape
                                    )
                            ) {
                                Image(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add/Edit note",
                                    modifier = Modifier
                                        .size(40.dp)
                                )
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ) {
                    RootNavGraph(
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}