package com.example.notes.notes_future.presentation.addEditNote.compose

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_notes.presentation.addEditNote.AddEditNoteEvent
import com.example.notes.feature_notes.presentation.addEditNote.AddEditNoteViewModel
import com.example.notes.feature_notes.presentation.addEditNote.UiEvent
import com.example.notes.notes_future.present.addEditNote.compose.TextField
import com.example.notes.notes_future.present.notes.compose.ButtonWithImage
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditPresentation(
    navHostController: NavHostController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val titleState = viewModel.title.value
    val contentState = viewModel.content.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.SaveNote -> {
                    navHostController.navigateUp()
                }
                is UiEvent.ShowSnackbar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (titleState.text.isNotEmpty() && contentState.text.isNotEmpty()) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    },
                    backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
                    modifier = Modifier
                        .size(80.dp)
                        .border(
                            width = 10.dp,
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape
                        )
                ) {
                    Image(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Add/Edit note",
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                text = titleState.text,
                placeholder = titleState.placeholder,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isPlaceholder = titleState.isPlaceholder,
                singleLine = true,
                textStyle = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                text = contentState.text,
                placeholder = contentState.placeholder,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isPlaceholder = contentState.isPlaceholder,
                textStyle = MaterialTheme.typography.headlineSmall
            )
        }
    }
}