package com.example.notes.notes_future.present.addEditNote.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextField(
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPlaceholder: Boolean = true,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onFocusChange: (FocusState) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }
        )

        if(isPlaceholder) {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.outline,
                style = textStyle
            )
        }
    }

}