package com.lid.dailydoc.repositories

import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeNoteRepository : NoteRepository {

    private val notes by lazy {
        listOf(
            Note("First Date", 1L, "testSummary", "testBody",
            "survey1Test", "survey2Test", "survey3Test"),
            Note("Second Date", 2L, "testSummary", "testBody",
                "survey1Test", "survey2Test", "survey3Test"),
        )
    }
    private val noteList = MutableStateFlow(notes)


    override val allNotes: Flow<List<Note>> = noteList

    override suspend fun insertNote(note: Note) {

    }

    override fun findNoteById(noteId: Long): Note {
        var foundNote = Note("")
        for (note in notes) {
            if (note.id == noteId) foundNote = note
        }
        return foundNote
    }

    override fun exists(date: String): Flow<Boolean> {
        var exists: Flow<Boolean> = MutableStateFlow(false)
        for (note in notes) {
            if (note.dateCreated == date) exists = MutableStateFlow(true)
        }
        return exists
    }

    override fun noteExists(date: String): Boolean {
        TODO("Not yet implemented")
    }


    override fun findNoteByDate(date: String): Note {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(note: Note) {
        TODO("Not yet implemented")
    }
}