package com.example.notes.notes_future.presentation.notes.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.presentation.notes.NotesViewModel
import com.example.notes.core.util.graph.Screen
import com.example.notes.feature_notes.model.GridCellEnum

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState",
    "AutoboxingStateCreation"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesPresentation(
    navHostController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    var gridCell by mutableStateOf(GridCellEnum.Grid)
    val state = viewModel.state.value

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Notes",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Icon(
                    imageVector = if (gridCell == GridCellEnum.Grid) Icons.Filled.List
                                else Icons.Filled.GridView,
                    contentDescription = "Grid",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            if (gridCell == GridCellEnum.Flat) gridCell = GridCellEnum.Grid
                            else gridCell = GridCellEnum.Flat
                        }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if(state.notes.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(
                        if (gridCell == GridCellEnum.Grid) 2
                        else 1
                    )
                ) {
                    itemsIndexed(state.notes) { index: Int, note: Note ->
                        NoteItem(
                            navHostController = navHostController,
                            viewModel = viewModel,
                            note = note,
                            isSecondColor = if (gridCell == GridCellEnum.Grid) {
                                if ((index / 2)%2 == 1) {
                                    index % 2 == 0
                                } else {
                                    index % 2 == 1
                                }
                            } else {
                                index % 2 == 0
                            }
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