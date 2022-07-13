package com.example.notes.notes_future.present.notes.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.notes_future.present.notes.NotesViewModel
import com.example.notes.notes_future.present.util.Screen

@Composable
fun NotesPresent(
    navHostController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Box(
        contentAlignment = Alignment.BottomEnd
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
        ButtonWithImage(
            image = Icons.Filled.Add,
            onClick = {
                navHostController.navigate(Screen.AddEdit.route)
            }
        )
    }
}