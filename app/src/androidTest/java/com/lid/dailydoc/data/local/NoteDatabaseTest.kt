package com.lid.dailydoc.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.utils.getCurrentDateAsString
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest : TestCase() {

    private lateinit var db: NoteDatabase
    private lateinit var dao: NoteDao

    @Before
    override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).build()
        dao = db.noteDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadNote() = runBlocking {
        val date = getCurrentDateAsString()
        val note = Note(date)
        dao.insertNote(note)
        val retrievedNote = dao.getNoteByDate(date)
        assertEquals(note, retrievedNote)
    }
}