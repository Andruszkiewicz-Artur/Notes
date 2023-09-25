package com.example.notes.feature_notes.presentation.addEditNote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.domain.use_case.local.NotesUseCases
import com.example.notes.feature_notes.domain.use_case.remote.RemoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.notes.core.value.Static
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    savedStateHandle: SavedStateHandle,
    private val remoteUseCases: RemoteUseCases
): ViewModel() {

    private val _state = MutableStateFlow(AddEditNoteState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentId: Int? = null
    var createTime: Long? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    notesUseCases.getNoteByIdUseCase.invoke(noteId)?.also { note ->
                        currentId = note.id
                        createTime = note.timeCreate

                        _state.update { it.copy(
                            title = note.title,
                            content = note.content
                        ) }
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _state.update { it.copy(
                    title = event.value
                ) }
            }
            is AddEditNoteEvent.EnteredContent -> {
                _state.update { it.copy(
                    content = event.value
                ) }
            }
            is AddEditNoteEvent.SaveNote -> {
                if (_state.value.title.isNotEmpty() && _state.value.content.isNotEmpty()) {
                    val currentTime = System.currentTimeMillis()

                    val note = Note(
                        id = currentId,
                        title = _state.value.title,
                        content = _state.value.content,
                        timeCreate = createTime ?: currentTime,
                        timeUpdate = currentTime,
                        isDeleted = false
                    )

                    viewModelScope.launch {
                        notesUseCases.insertNoteUseCase.invoke(note)
                        if (Static.profileSetting?.isSynchronize == true) remoteUseCases.uploadNoteUseCase.execute(note)

                        _state.update { it.copy(
                            noteIsSaved = true
                        ) }
                    }
                }
            }
        }
    }
}