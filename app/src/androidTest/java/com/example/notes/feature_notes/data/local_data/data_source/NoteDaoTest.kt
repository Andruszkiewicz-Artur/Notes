package com.example.notes.feature_notes.data.local_data.data_source

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.notes.notes_future.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var notesDao: NotesDao

    @Before
    fun setUp() {
        notesDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotesDatabase::class.java
        ).allowMainThreadQueries().build()
        notesDao = notesDatabase.notesDao
    }

    @After
    fun tearDown() {
        notesDatabase.close()
    }

    @Test
    fun getAllNotes_SeeAllAddingNotes() = runBlocking {
        val note = Note(
            id = 1,
            title = "title",
            content = "description",
            timeCreate = System.currentTimeMillis(),
            timeUpdate = System.currentTimeMillis(),
            isDeleted = false
        )

        notesDao.insertNote(note)
        notesDao.insertNote(note.copy(id = 2))
        notesDao.insertNote(note.copy(id = 3))

        val allNotesCount = notesDao.getAllNotes().first().count()

        assertThat(allNotesCount).isEqualTo(3)
    }

    @Test
    fun insertingNoteToDatabase_MustSeeThatWork() = runBlocking {
        val note = Note(
            id = 1,
            title = "title",
            content = "description",
            timeCreate = System.currentTimeMillis(),
            timeUpdate = System.currentTimeMillis(),
            isDeleted = false
        )
        notesDao.insertNote(note)

        val getNote = notesDao.getNoteById(1)

        assertThat(getNote).isEqualTo(note)
    }

    @Test
    fun getNoteById_MustBeOnTheDatabase() = runBlocking {
        val noteId = 4
        val note = Note(
            id = noteId,
            title = "title",
            content = "description",
            timeCreate = System.currentTimeMillis(),
            timeUpdate = System.currentTimeMillis(),
            isDeleted = false
        )
        notesDao.insertNote(note)

        val getNote = notesDao.getNoteById(noteId)

        assertThat(getNote).isEqualTo(note)
    }

    @Test
    fun updateExistingNote_MustUpdateChangeOldValue() = runBlocking {
        val noteId = 4
        val note = Note(
            id = noteId,
            title = "title",
            content = "description",
            timeCreate = System.currentTimeMillis(),
            timeUpdate = System.currentTimeMillis(),
            isDeleted = false
        )
        notesDao.insertNote(note)
        notesDao.insertNote(note.copy(
            title = "title2",
            content = "description2"
        ))
        val getNote = notesDao.getNoteById(4)

        assertThat(getNote).isEqualTo(note.copy(
            title = "title2",
            content = "description2"
        ))
    }

    @Test
    fun deletingNote_CantSeeNote() = runBlocking {
        val noteId = 4
        val note = Note(
            id = noteId,
            title = "title",
            content = "description",
            timeCreate = System.currentTimeMillis(),
            timeUpdate = System.currentTimeMillis(),
            isDeleted = false
        )
        notesDao.insertNote(note)
        notesDao.deleteNote(note)
        val getNote = notesDao.getNoteById(4)

        assertThat(getNote).isNull()
    }
}