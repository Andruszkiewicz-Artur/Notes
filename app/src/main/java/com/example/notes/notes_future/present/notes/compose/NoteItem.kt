package com.example.notes.notes_future.present.notes.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.notes_future.present.notes.NotesViewModel
import com.example.notes.notes_future.present.util.Screen

@Composable
fun NoteItem(
    navHostController: NavHostController,
    viewModel: NotesViewModel,
    note: Note
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                navHostController.navigate(Screen.AddEdit.route)
            }
    ) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = note.content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = rememberVectorPainter(image = Icons.Filled.Delete),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        viewModel.deleteNote(note)
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}