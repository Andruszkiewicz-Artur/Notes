package com.example.notes.feature_notes.presentation.notes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.value.profileSetting
import com.example.notes.feature_notes.data.mapper.toNote
import com.example.notes.feature_notes.domain.model.RemoteNoteModel
import com.example.notes.feature_notes.domain.repository.NotesRemoteRepository
import com.example.notes.feature_notes.domain.unit.Resource
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.domain.use_case.local.NotesUseCases
import com.example.notes.feature_notes.domain.use_case.remote.RemoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    private val remoteUseCases: RemoteUseCases
): ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    init {
        getAllNotes()

        if (profileSetting?.isSynchronize == null) {
            viewModelScope.launch {
                val result = remoteUseCases.checkIsSynchronize.execute()
                viewModelScope.launch {
                    val remoteNotes = remoteUseCases.takeAllNotesUseCase.execute()

                    when (remoteNotes) {
                        is Resource.Error -> {
                            Log.d("Check", "error send")
                            Log.d("Error taking remote notes", remoteNotes.message ?: "Unknown error!")
                        }
                        is Resource.Success -> {
                            Log.d("Check", "Start delay")
                            delay(2000)
                            Log.d("Check", "Stop delay")

                            if(remoteNotes.data != null) {
                                synchronizeData(remoteNotes.data)
                            }
                        }
                    }
                }

                when (result) {
                    is Resource.Error -> {
                        Log.d("Error isSynchronize", result.message.toString())
                    }
                    is Resource.Success -> {
                        profileSetting?.isSynchronize = result.data
                    }
                }
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesUseCases.deleteNoteUseCase.invoke(note)
            getAllNotes()
        }
    }

    private fun getAllNotes() {
        notesUseCases.getAllNotesUseCase.invoke().onEach { notes ->
            _state.value = state.value.copy(
                notes = notes
            )
        }.launchIn(viewModelScope)
    }

    private fun synchronizeData(remoteList: List<RemoteNoteModel>) {
        viewModelScope.launch {
            remoteList.forEach { remoteNote ->
                var include = false

                _state.value.notes.forEach {
                    if(it.timeCreate == remoteNote.id.toLong()) {
                        include = true
                    }
                }

                if (!include) {
                    notesUseCases.insertNoteUseCase.invoke(
                        remoteNote.toNote()
                    )
                }
            }
        }
    }
}