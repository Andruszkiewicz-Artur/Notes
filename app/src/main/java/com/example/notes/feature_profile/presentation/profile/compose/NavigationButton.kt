package com.example.notes.feature_profile.presentation.profile.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notes.core.util.graph.Screen

@Composable
fun NavigationButton(
    text: String,
    isBorder: Boolean,
    onClick: () -> Unit
) {
    Button(
        colors = if (isBorder) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background) else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        shape = CircleShape,
        border = if (isBorder) BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary) else null,
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = if (isBorder) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary
        )
    }
}