package com.example.notes.notes_future.present.notes.compose

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notes.notes_future.present.util.Screen

@Composable
fun ButtonWithImage(
    image: ImageVector,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(end = 16.dp, bottom = 60.dp)
            .shadow(
                elevation = 10.dp,
                shape = CircleShape
            )
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = CircleShape
            )
            .clickable {
                onClick()
            }
    ) {
        Icon(
            imageVector = image,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp)
        )
    }
}