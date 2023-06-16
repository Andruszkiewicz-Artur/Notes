package com.example.notes.feature_notes.presentation.addEditNote

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.feature_notes.data.mapper.toRemoteNote
import com.example.notes.feature_notes.domain.model.StatusNoteEnum
import com.example.notes.notes_future.domain.model.InvalidNoteException
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.feature_notes.domain.use_case.local.NotesUseCases
import com.example.notes.feature_notes.domain.use_case.remote.RemoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.notes.R

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    savedStateHandle: SavedStateHandle,
    private val remoteUseCases: RemoteUseCases
): ViewModel() {

    private val _title = mutableStateOf(
        TextFieldState(
            placeholder = R.string.Title.toString()
        )
    )
    val title: State<TextFieldState> = _title

    private val _content = mutableStateOf(
        TextFieldState(
            placeholder = R.string.AddContent.toString()
        )
    )
    val content: State<TextFieldState> = _content

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    notesUseCases.getNoteByIdUseCase.invoke(noteId)?.also { note ->
                        currentId = note.id
                        _title.value = title.value.copy(
                            text = note.title,
                            isPlaceholder = false
                        )
                        _content.value = content.value.copy(
                            text = note.content,
                            isPlaceholder = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _title.value = title.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _title.value = title.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _title.value.text.isEmpty()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _content.value = content.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _content.value = content.value.copy(
                    isPlaceholder = !event.focusState.isFocused && _content.value.text.isEmpty()
                )
            }
            is AddEditNoteEvent.SaveNote -> {
                if (_title.value.text.isNotEmpty() && _content.value.text.isNotEmpty()) {
                    val currentTime = System.currentTimeMillis()

                    val note = Note(
                        id = currentId ?: currentTime.toInt(),
                        title = _title.value.text,
                        content = _content.value.text,
                        timeCreate = currentTime,
                        status = StatusNoteEnum.Local
                    )

                    viewModelScope.launch {
                        val result = remoteUseCases.uploadNoteUseCase.execute(note.toRemoteNote())
                        if(!result.successful) {
                            Log.d("Problem with add note to firebase", result.errorMessage ?: "Unknown error")
                        }

                        if(result.successful) {
                            note.status = StatusNoteEnum.Sending
                        }

                        try {
                            notesUseCases.insertNoteUseCase.invoke(note)
                            _eventFlow.emit(UiEvent.SaveNote)
                        } catch (e: InvalidNoteException) {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = e.message ?: R.string.CouldntSaveNote.toString()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}