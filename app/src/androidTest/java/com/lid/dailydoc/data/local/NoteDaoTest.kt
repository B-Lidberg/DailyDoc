package com.lid.dailydoc.data.local

import org.junit.Assert.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class NoteDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: NoteDatabase
    private lateinit var dao: NoteDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.noteDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun database_is_empty() {
        val allNotes = dao.getAllNotes().asLiveData().getOrAwaitValue()
        assertTrue(allNotes.isEmpty())
    }

    @Test
    fun insert_note_into_database() = runBlockingTest {
        val note = Note(dateCreated = "Saturday May 1, 2021", id = 1L,
            summary = "Test Summary", body = "Test Body",
            survey1 = "Yes", survey2 = "No", survey3 = "Maybe"
        )
        dao.insertNote(note)

        val allNotes = dao.getAllNotes().asLiveData().getOrAwaitValue()
        assertTrue(allNotes.contains(note))
    }

    @Test
    fun delete_single_note_from_database() = runBlockingTest {
        val note = Note(dateCreated = "Saturday May 1, 2021", id = 1L,
            summary = "Test Summary", body = "Test Body",
            survey1 = "Yes", survey2 = "No", survey3 = "Maybe"
        )
        dao.insertNote(note)
        dao.deleteNote(note.dateCreated)

        val allNotes = dao.getAllNotes().asLiveData().getOrAwaitValue()
        assertFalse(allNotes.contains(note))
    }

    @Test
    fun fetch_all_notes_from_database() = runBlockingTest {
        val note = Note(dateCreated = "Saturday May 1, 2021", id = 1L,
            summary = "Test Summary", body = "Test Body",
            survey1 = "Yes", survey2 = "No", survey3 = "Maybe"
        )
        val note2 = Note(dateCreated = "Saturday May 1, 2020", id = 2L,
            summary = "Test Summary", body = "Test Body",
            survey1 = "Yes", survey2 = "No", survey3 = "Maybe"
        )
        dao.insertNote(note)
        dao.insertNote(note2)
        val listOfNotes: List<Note> = listOf(note, note2)
        val allNotes = dao.getAllNotes().asLiveData().getOrAwaitValue()
        assertEquals(listOfNotes, allNotes)
    }

    @Test
    fun find_noteById_in_database() = runBlockingTest {
        val note = Note(dateCreated = "Saturday May 1, 2021", id = 1L,
            summary = "Test Summary", body = "Test Body",
            survey1 = "Yes", survey2 = "No", survey3 = "Maybe"
        )
        dao.insertNote(note)

        val fetchNote = dao.findById(note.id)
        assertEquals(note, fetchNote)
    }

    @Test
    fun note_exists_in_database() = runBlockingTest {
        val note = Note(dateCreated = "Saturday May 1, 2021", id = 1L,
            summary = "Test Summary", body = "Test Body",
            survey1 = "Yes", survey2 = "No", survey3 = "Maybe"
        )
        dao.insertNote(note)

        val exists = dao.exists(note.dateCreated).asLiveData().getOrAwaitValue()

        assertTrue(exists)
    }

    @Test
    fun update_note_in_database() = runBlockingTest {
        val note = Note(dateCreated = "Saturday May 1, 2021", id = 1L,
            summary = "Test Summary", body = "Test Body",
            survey1 = "Yes", survey2 = "No", survey3 = "Maybe"
        )
        dao.insertNote(note)
        note.summary = "NEW"
        dao.updateNote(note)

        val fetchNote = dao.findById(note.id)

        assertEquals(fetchNote.summary, "NEW")
    }
}