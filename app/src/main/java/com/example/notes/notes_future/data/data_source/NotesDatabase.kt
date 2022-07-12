package com.example.notes.notes_future.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.notes_future.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NotesDatabase: RoomDatabase() {

    abstract val notesDao: NotesDao

    companion object {
        val DATABASE_NAME = "notes_database"
    }

}