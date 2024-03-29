package com.example.notes.notes_future.presentation.notes.compose

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.R
import com.example.notes.core.util.graph.Screen
import com.example.notes.core.value.Static
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.presentation.notes.NotesViewModel
import com.example.notes.feature_notes.domain.model.GridCellEnum
import com.example.notes.feature_notes.presentation.notes.NotesEvent
import com.example.notes.feature_notes.utils.test.TestTags
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState",
    "AutoboxingStateCreation"
)
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class, ExperimentalMaterialApi::class
)
@Composable
fun NotesPresentation(
    navHostController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    val pullRefreshState = rememberSwipeRefreshState(state.isLoading)

    LaunchedEffect(key1 = Static.profileSetting?.isSynchronize) {
        viewModel.onEvent(NotesEvent.SynchronizeData)
    }

    SwipeRefresh(
        state = pullRefreshState,
        onRefresh = {
            viewModel.onEvent(NotesEvent.SynchronizeData)
        },
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
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
                        text = stringResource(id = R.string.notes),
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    AnimatedContent(
                        targetState = state.typeOfPresentingList,
                        transitionSpec = {
                            fadeIn(
                                animationSpec = tween(500)
                            ) togetherWith fadeOut(
                                animationSpec = tween(500)
                            )
                        },
                        label = stringResource(id = R.string.Grid_type)
                    ) {
                        if (it != GridCellEnum.Grid) {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(NotesEvent.ChangeTypeOfPresentingList)
                                },
                                modifier = Modifier
                                    .testTag(TestTags.GridCell_TAG)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.GridView,
                                    contentDescription = stringResource(id = R.string.Grid_type),
                                    modifier = Modifier
                                        .size(50.dp)
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(NotesEvent.ChangeTypeOfPresentingList)
                                },
                                modifier = Modifier
                                    .testTag(TestTags.FlatCell_TAG)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.List,
                                    contentDescription = stringResource(id = R.string.Grid_type),
                                    modifier = Modifier
                                        .size(50.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (state.notes.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(
                            if (state.typeOfPresentingList == GridCellEnum.Grid) 2
                            else 1
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        itemsIndexed(state.notes) { index: Int, note: Note ->
                            NoteItem(
                                note = note,
                                isSecondColor = if (state.typeOfPresentingList == GridCellEnum.Grid) {
                                    if ((index / 2) % 2 == 1) {
                                        index % 2 == 0
                                    } else {
                                        index % 2 == 1
                                    }
                                } else {
                                    index % 2 == 0
                                },
                                onClickNote = {
                                    navHostController.navigate(Screen.AddEdit.sendNoteId(note.id ?: 0))
                                },
                                onClickDeleteNote = {
                                    viewModel.onEvent(NotesEvent.RemoveNote(note))
                                },
                                modifier = Modifier
                                    .animateItemPlacement(
                                        animationSpec = tween(
                                            durationMillis = 1000
                                        )
                                    )
                            )
                            if (index == state.notes.size - 1) {
                                Spacer(modifier = Modifier.height(120.dp))
                            }
                        }
                    }
                } else {
                    Text(
                        text = stringResource(id = R.string.None_notes_yet),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(TestTags.IsEmptyListText_TAG)
                    )
                }
            }
        }
    }
}