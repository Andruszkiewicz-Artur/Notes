package com.example.notes.notes_future.presentation.addEditNote

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.notes_future.domain.model.InvalidNoteException
import com.example.notes.notes_future.domain.model.Note
import com.example.notes.notes_future.domain.use_case.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _title = mutableStateOf(
        TextFieldState(
        placeholder = "Title..."
    )
    )
    val title: State<TextFieldState> = _title

    private val _content = mutableStateOf(
        TextFieldState(
        placeholder = "Add content..."
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
                    viewModelScope.launch {
                        try {
                            notesUseCases.insertNoteUseCase.invoke(
                                Note(
                                    id = currentId,
                                    title = _title.value.text,
                                    content = _content.value.text,
                                    timeCreate = System.currentTimeMillis()
                                )
                            )
                            _eventFlow.emit(UiEvent.SaveNote)
                        } catch (e: InvalidNoteException) {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = e.message ?: "Couldn't`t save note!"
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}