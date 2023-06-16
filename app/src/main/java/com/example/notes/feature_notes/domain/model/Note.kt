package com.example.notes.notes_future.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.feature_notes.domain.model.StatusNoteEnum

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "id")
    @PrimaryKey val id: Int? = null,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "timeCreate")
    val timeCreate: Long,

    @ColumnInfo(name = "status")
    var status: StatusNoteEnum
)

class InvalidNoteException(message: String): Exception(message)
