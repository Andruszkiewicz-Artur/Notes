package com.example.notes.feature_notes.presentation.notes

import com.example.notes.notes_future.domain.model.Note

sealed class NotesEvent {
    data class RemoveNote(val note: Note): NotesEvent()
    data object SynchronizeData: NotesEvent()
    data object ChangeTypeOfPresentingList: NotesEvent()
}
