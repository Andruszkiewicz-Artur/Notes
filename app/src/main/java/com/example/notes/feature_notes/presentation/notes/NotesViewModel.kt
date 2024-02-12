package com.example.notes.feature_notes.presentation.notes

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.model.ProfileModel
import com.example.notes.core.util.extensions.toast
import com.example.notes.core.value.Static
import com.example.notes.feature_notes.data.mapper.toNote
import com.example.notes.feature_notes.data.mapper.toRemoteNote
import com.example.notes.feature_notes.domain.model.GridCellEnum
import com.example.notes.feature_notes.domain.model.RemoteNoteModel
import com.example.notes.feature_notes.domain.model.StatusNoteEnum
import com.example.notes.feature_notes.domain.unit.Resource
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.domain.use_case.local.NotesUseCases
import com.example.notes.feature_notes.domain.use_case.remote.RemoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    private val remoteUseCases: RemoteUseCases,
    private val application: Application
): ViewModel() {

    companion object {
        private const val TAG = "NotesViewModel_TAG"
    }

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    init {
        getNotes()
        checkSynchronize()
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            NotesEvent.SynchronizeData -> {
                if (Static.profileSetting?.isSynchronize == true) {
                    viewModelScope.launch {
                        val remoteNotes = remoteUseCases.takeAllNotesUseCase.execute()

                        if (remoteNotes.data != null) {
                            val remoteData = remoteNotes.data.map { it.toNote() }
                            _state.update { it.copy(isLoading = true) }

                            remoteData
                                .mapNotNull { remoteNote ->
                                    if (_state.value.allNotes.none { it.timeCreate == remoteNote.timeCreate }) remoteNote

                                    else {
                                        val note = _state.value.allNotes.first { it.timeCreate == remoteNote.timeCreate }

                                        if (note.timeUpdate < remoteNote.timeUpdate) remoteNote
                                        else null
                                    }
                                }
                                .forEach { note ->
                                    notesUseCases.insertNoteUseCase.invoke(note)
                                }

                            _state.value.allNotes
                                .mapNotNull { localNote ->
                                    if (remoteData.none { it.timeCreate == localNote.timeCreate }) localNote
                                    else {
                                        val note = remoteData.first { it.timeCreate == localNote.timeCreate }

                                        if (note.timeUpdate < localNote.timeUpdate) localNote
                                        else null
                                    }
                                }
                                .forEach { note ->
                                    remoteUseCases.uploadNoteUseCase.execute(note)
                                }

                            _state.update { it.copy(isLoading = false) }
                        } else {
                            toast("${remoteNotes.message}", application)
                        }
                    }
                }
            }
            is NotesEvent.RemoveNote -> {
                viewModelScope.launch {
                    val newNote = event.note.copy(
                        isDeleted = true
                    )

                    if (Static.profileSetting?.isSynchronize == true) {
                        val result = remoteUseCases.deleteNoteUseCase.execute(event.note)

                        if (result.successful) {
                            notesUseCases.insertNoteUseCase.invoke(newNote)
                        } else {
                            toast("${result.errorMessage}", application)
                        }
                    } else {
                        notesUseCases.insertNoteUseCase.invoke(newNote)
                    }
                }
            }
            NotesEvent.ChangeTypeOfPresentingList -> {
                val newTypeOfSorting = when (_state.value.typeOfPresentingList) {
                    GridCellEnum.Grid -> GridCellEnum.Flat
                    GridCellEnum.Flat -> GridCellEnum.Grid
                }
                _state.update { it.copy(
                    typeOfPresentingList = newTypeOfSorting
                ) }
            }
        }
    }

    private fun getNotes() {
        notesUseCases.getAllNotesUseCase.invoke().onEach { notes ->
            _state.value = state.value.copy(
                allNotes = notes,
                notes = notes.filter { !it.isDeleted }
            )
        }.launchIn(viewModelScope)
    }

    private fun checkSynchronize() {
        viewModelScope.launch {
            val result = remoteUseCases.checkIsSynchronize.execute()

            if (result.data == true) {
                Static.profileSetting = ProfileModel(isSynchronize = true)
            }
        }
    }
}

