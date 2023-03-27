package com.example.notes.core.compose.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ListButton(
    text: String,
    onClick: () -> Unit,
    isBottomBar: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .fillMaxWidth()
        ) {
            Text(text = text)

            Spacer(modifier = Modifier.fillMaxWidth())

            Icon(
                Icons.Rounded.ArrowForward,
                contentDescription = "Localized description"
            )
        }

        if (isBottomBar) {
            Divider()
        }
    }
}