package com.example.notes.notes_future.presentation.notes.compose

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
import com.example.notes.notes_future.presentation.notes.NotesViewModel
import com.example.notes.core.util.graph.Screen

@Composable
fun NoteItem(
    navHostController: NavHostController,
    viewModel: NotesViewModel,
    note: Note,
    isEven: Boolean
) {
    val label = if(isEven) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if(isEven) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                navHostController.navigate(Screen.AddEdit.sendNoteId(note.id!!))
            }
            .padding(16.dp)
    ) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.titleLarge,
            color = label
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = note.content,
            style = MaterialTheme.typography.bodyMedium,
            color = label
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                painter = rememberVectorPainter(image = Icons.Filled.Delete),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        viewModel.deleteNote(note)
                    },
                tint = label
            )
        }
    }
}