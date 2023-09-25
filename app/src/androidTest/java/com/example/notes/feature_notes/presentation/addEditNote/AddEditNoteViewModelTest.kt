package com.example.notes.feature_notes.presentation.addEditNote

import androidx.lifecycle.SavedStateHandle
import com.example.notes.feature_notes.domain.use_case.local.NotesUseCases
import com.example.notes.feature_notes.domain.use_case.remote.RemoteUseCases
import com.example.notes.notes_future.domain.model.Note
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class AddEditNoteViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: AddEditNoteViewModel
    private lateinit var notesUseCases: NotesUseCases
    private lateinit var remoteUseCases: RemoteUseCases

    @Before
    fun setUp() {
        notesUseCases = mockk {
            coEvery { insertNoteUseCase.invoke(any()) } returns mockk()
            coEvery { getNoteByIdUseCase.invoke(any()) } returns Note(
                1,
                "title",
                "content",
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                isDeleted = false
            )
        }
        remoteUseCases = mockk()

        val stateHandle = mapOf<String, Any>(Pair("noteId", 1))

        viewModel = AddEditNoteViewModel(
            notesUseCases,
            SavedStateHandle(stateHandle),
            remoteUseCases
        )

        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun enteredTitle_StateContainsValue() {
        viewModel.onEvent(AddEditNoteEvent.EnteredTitle("title"))

        assertThat(viewModel.state.value.title).isEqualTo("title")
    }


    @Test
    fun enteredContent_StateContainsValue() {
        viewModel.onEvent(AddEditNoteEvent.EnteredContent("content"))

        assertThat(viewModel.state.value.content).isEqualTo("content")
    }

    @Test
    fun clickSaveNoteWithEmptyFields_CantSaveNote() = runBlocking {
        viewModel.onEvent(AddEditNoteEvent.SaveNote)

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.noteIsSaved).isFalse()
    }

    @Test
    fun clickSaveNoteWithData_SaveNote() = runBlocking {
        viewModel.onEvent(AddEditNoteEvent.EnteredTitle("title"))
        viewModel.onEvent(AddEditNoteEvent.EnteredContent("content"))
        viewModel.onEvent(AddEditNoteEvent.SaveNote)

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.noteIsSaved).isTrue()
    }
}