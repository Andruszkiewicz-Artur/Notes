package com.example.notes.feature_profile.unit.comp

import android.graphics.drawable.VectorDrawable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leftIcon: ImageVector,
    imeAction: ImeAction = ImeAction.Next,
    onNext: () -> Unit = { },
    onDone: () -> Unit = { },
    clickVisibilityPassword: () -> Unit = {  },
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    errorMessage: String? = null,
    autoCorrect: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    modifier: Modifier = Modifier
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(
                    text = label
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = autoCorrect,
                capitalization = capitalization,
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { onNext() },
                onDone = { onDone() }
            ),
            leadingIcon = {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (isPassword) {
                    AnimatedContent(
                        targetState = showPassword,
                        label = "label"
                    ) { isPresented ->
                        if(isPresented) {
                            Icon(
                                imageVector = Icons.Rounded.Visibility,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    clickVisibilityPassword()
                                }
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Rounded.VisibilityOff,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    clickVisibilityPassword()
                                }
                            )
                        }
                    }
                }
            },
            singleLine = true,
            visualTransformation = if (isPassword) { if (!showPassword) PasswordVisualTransformation() else  VisualTransformation.None } else { VisualTransformation.None },
            isError = errorMessage != null,
            modifier = modifier
                .fillMaxWidth()
        )

        AnimatedVisibility(visible = errorMessage != null) {
            Text(
                text = "$errorMessage",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red,
                fontWeight = FontWeight.Thin
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun previewTextField() {
    TextField(
        value = "artur",
        onValueChange = {

        },
        label = "Email",
        errorMessage = "Empty field!",
        leftIcon = Icons.Rounded.Email,
    )
}