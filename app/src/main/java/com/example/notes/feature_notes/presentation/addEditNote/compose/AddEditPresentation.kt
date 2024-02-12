package com.example.notes.notes_future.presentation.addEditNote.compose

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.feature_notes.presentation.addEditNote.AddEditNoteEvent
import com.example.notes.feature_notes.presentation.addEditNote.AddEditNoteViewModel
import com.example.notes.feature_notes.presentation.addEditNote.UiEvent
import com.example.notes.notes_future.present.addEditNote.compose.TextField
import kotlinx.coroutines.flow.collectLatest
import com.example.notes.R
import com.example.notes.feature_notes.presentation.addEditNote.AddEditNoteUiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditPresentation(
    navHostController: NavHostController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                AddEditNoteUiEvent.SaveNote -> {
                    navHostController.popBackStack()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (state.title.isNotBlank() && state.content.isNotBlank()) {
                ExtendedFloatingActionButton(
                    onClick = {
                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = R.string.AddEditNote.toString(),
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
                text = state.title,
                placeholder = stringResource(id = R.string.Title),
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                text = state.content,
                placeholder = stringResource(id = R.string.AddContent),
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                textStyle = MaterialTheme.typography.headlineSmall
            )
        }
    }
}