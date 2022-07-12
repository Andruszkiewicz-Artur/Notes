package com.example.notes.notes_future.present.notes.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.notes_future.present.notes.NotesViewModel

@Composable
fun NotesPresent(
    navHostController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        AddItem(
            navHostController = navHostController
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Notes",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            if(state.notes.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.notes) { note ->
                        NoteItem(
                            navHostController = navHostController,
                            viewModel = viewModel,
                            note = note
                        )
                    }
                }
            } else {
                Text(
                    text = "None notes yet!",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}