package com.example.notes.notes_future.presentation.notes.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.notes_future.present.notes.compose.ButtonWithImage
import com.example.notes.feature_notes.presentation.notes.NotesViewModel
import com.example.notes.core.util.graph.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesPresentation(
    navHostController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Scaffold(
        floatingActionButton = {
            ButtonWithImage(
                image = Icons.Filled.Add,
                onClick = {
                    navHostController.navigate(Screen.AddEdit.route)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
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
                    itemsIndexed(state.notes) { index: Int, note: Note ->
                        NoteItem(
                            navHostController = navHostController,
                            viewModel = viewModel,
                            note = note,
                            isEven = index%2 == 0
                        )
                        if(index == state.notes.size - 1) {
                            Spacer(modifier = Modifier.height(120.dp))
                        }
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