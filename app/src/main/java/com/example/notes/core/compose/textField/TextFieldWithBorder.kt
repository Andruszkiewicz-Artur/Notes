package com.example.notes.core.compose.textField

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.notes.notes_future.present.addEditNote.compose.TextField

@Composable
fun TextFieldWithBorder(
    text: String,
    placeholder: String,
    isPlaceholder: Boolean = true,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onFocusChange: (FocusState) -> Unit
) {
    TextField(
        text = text,
        placeholder = placeholder,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(
                color = MaterialTheme.colorScheme.primary,
                width = 2.dp,
                shape = CircleShape
            )
            .padding(horizontal = 10.dp),
        isPlaceholder = isPlaceholder,
        onValueChange = {
            onValueChange(it)
        },
        singleLine = singleLine,
        textStyle = textStyle,
        onFocusChange = {
            onFocusChange(it)
        }
    )
}