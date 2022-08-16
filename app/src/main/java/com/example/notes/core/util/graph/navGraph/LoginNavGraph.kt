package com.example.notes.notes_future.presentation.util.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.notes.notes_future.presentation.forgotPassword.compose.ForgetPasswordPresentation
import com.example.notes.notes_future.presentation.login.compose.LoginPresentation
import com.example.notes.notes_future.presentation.register.compose.RegistrationPresentation
import com.example.notes.core.util.graph.Graph
import com.example.notes.core.util.graph.Screen

fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController
) {
    navigation(
        route =  Graph.LOGIN,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Login.route
        ) {
            LoginPresentation(
                navController = navController
            )
        }
        composable(
            route = Screen.Register.route
        ) {
            RegistrationPresentation(
                navController = navController
            )
        }
        composable(
            route = Screen.ForgetPassword.route
        ) {
            ForgetPasswordPresentation(
                navController = navController
            )
        }
    }
}

