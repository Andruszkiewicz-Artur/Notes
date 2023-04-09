package com.example.notes.feature_notes.data.local_data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.notes_future.domain.model.Note

@Database(
    entities = [Note::class],
    version = 2
)
abstract class NotesDatabase: RoomDatabase() {

    abstract val notesDao: NotesDao

    companion object {
        val DATABASE_NAME = "notes_database"
    }

}