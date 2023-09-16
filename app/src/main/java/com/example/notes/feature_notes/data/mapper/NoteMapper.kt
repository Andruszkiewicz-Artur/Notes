package com.example.notes.feature_notes.data.mapper

import com.example.notes.feature_notes.domain.model.RemoteContentNoteModel
import com.example.notes.feature_notes.domain.model.RemoteNoteModel
import com.example.notes.feature_notes.domain.model.StatusNoteEnum
import com.example.notes.notes_future.domain.model.Note

fun Note.toRemoteNote(): RemoteNoteModel {
    return RemoteNoteModel(
        id = timeCreate.toString(),
        value = RemoteContentNoteModel(
            title = title,
            content = content,
            updateTime = timeUpdate,
            isDeleted = isDeleted
        )
    )
}

fun RemoteNoteModel.toNote(): Note {
    return Note(
        id = null,
        title = value.title,
        content = value.content,
        timeUpdate = value.updateTime,
        timeCreate = id.toLong(),
        isDeleted = value.isDeleted
    )
}