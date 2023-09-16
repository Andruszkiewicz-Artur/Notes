package com.example.notes.feature_notes.presentation.addEditNote

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.core.compose.textField.TextFieldState
import com.example.notes.feature_notes.data.mapper.toRemoteNote
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
import com.example.notes.core.value.Static
import com.example.notes.feature_notes.domain.model.StatusNoteEnum

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    savedStateHandle: SavedStateHandle,
    private val remoteUseCases: RemoteUseCases,
    private val application: Application
): ViewModel() {

    private val _title = mutableStateOf(
        TextFieldState(
            placeholder = R.string.Title
        )
    )
    val title: State<TextFieldState> = _title

    private val _content = mutableStateOf(
        TextFieldState(
            placeholder = R.string.AddContent
        )
    )
    val content: State<TextFieldState> = _content

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
                        id = currentId,
                        title = _title.value.text,
                        content = _content.value.text,
                        timeCreate = createTime ?: currentTime,
                        timeUpdate = currentTime,
                        isDeleted = false
                    )

                    viewModelScope.launch {
                        notesUseCases.insertNoteUseCase.invoke(note)

                        if (Static.profileSetting?.isSynchronize == true) remoteUseCases.uploadNoteUseCase.execute(note)

                        _eventFlow.emit(UiEvent.SaveNote)

                    }
                }
            }
        }
    }
}