package com.example.notes.feature_profile.presentation.profile.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notes.core.util.graph.Screen

@Composable
fun LoginRegisterPresentation(
    navController: NavController
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You are now not jet logIn, please choose one of the option under.",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            NavigationButton(
                text = "Login",
                isBorder = false
            ) {
                navController.navigate(Screen.Login.route)
            }
            Text(
                text = "Or",
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(
                        vertical = 16.dp
                    )
            )
            NavigationButton(
                text = "Registration",
                isBorder = true
            ) {
                navController.navigate(Screen.Register.route)
            }
        }
    }
}