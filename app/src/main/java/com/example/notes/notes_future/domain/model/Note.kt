package com.example.notes.notes_future.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "id")
    @PrimaryKey val id: Int? = null,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "timeCreate")
    val timeCreate: Long
)

class InvalidNoteException(message: String): Exception(message)
