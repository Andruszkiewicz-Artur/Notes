package com.example.notes.feature_profile.presentation.unit.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ValidateText(
    text: String?,
    modifier: Modifier = Modifier,
    spaceModifier: Modifier = Modifier
        .height(20.dp)
) {
    AnimatedContent(
        targetState = text != null
    ) {
        if(it) {
            Text(
                text = text ?: "",
                color = Color.Red,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = 20.dp
                    )
                    .padding(
                        vertical = 8.dp
                    )
                    .padding(bottom = 8.dp)
                    .offset(
                        x = 10.dp
                    )
            )
        } else {
            Spacer(modifier = spaceModifier)
        }
    }
}