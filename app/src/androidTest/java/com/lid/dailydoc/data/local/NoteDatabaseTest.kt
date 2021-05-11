package com.lid.dailydoc.data.local

import org.junit.Assert.*
import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {

    private var db: NoteDatabase
    private var dao: NoteDao
    private var note: Note

    init {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.noteDao()
        note = Note(dateCreated = "Saturday May 1, 2021", id = 1L,
            summary = "Test Summary", body = "Test Body",
            survey1 = "Yes", survey2 = "No", survey3 = "Maybe"
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun database_is_empty() = runBlocking {
        val notes = dao.getAllNotes().asLiveData().value
        assertTrue(notes.isNullOrEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun database_is_not_empty() = runBlocking {
        dao.insertNote(note)
    }
    @Test
    @Throws(Exception::class)
    fun write_And_Read_Note_By_Id() = runBlocking {
        dao.insertNote(note)
        val retrieveNote = dao.findById(note.id)
        MatcherAssert.assertThat(note, CoreMatchers.equalTo(retrieveNote))
    }

    @Test
    @Throws(Exception::class)
    fun write_And_Read_Note_By_Date() = runBlocking {
        dao.insertNote(note)
        val retrieveNote = dao.getNoteByDate(note.dateCreated)
        assertTrue(dao.exists(retrieveNote.dateCreated))
    }

    @Test
    @Throws(Exception::class)
    fun delete_note_from_database() = runBlocking {
        dao.insertNote(note)
        dao.deleteNote(note.dateCreated)
        assertFalse(dao.exists(note.dateCreated))
    }
}