package com.example.notes.feature_notes.data.mapper

import com.example.notes.feature_notes.domain.model.RemoteContentNoteModel
import com.example.notes.feature_notes.domain.model.RemoteNoteModel
import com.example.notes.notes_future.domain.model.Note

fun Note.toRemoteNote(): RemoteNoteModel {
    return RemoteNoteModel(
        id = timeCreate.toString(),
        value = RemoteContentNoteModel(
            title = title,
            content = content,
            updateTime = timeCreate
        )
    )
}