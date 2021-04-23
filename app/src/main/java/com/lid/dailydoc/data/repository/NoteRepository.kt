package com.lid.dailydoc.data.repository

import androidx.annotation.WorkerThread
import com.lid.dailydoc.data.local.NoteDao
import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    fun findNoteById(noteId: Long) = noteDao.findById(noteId)

    fun noteExists(date: String): Boolean {
        return noteDao.exists(date)
    }
    fun exists(date: String): Flow<Boolean> {
        return noteDao.existsTest(date)
    }

    fun findNoteByDate(date: String): Note {
        return noteDao.getNoteByDate(date)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }
}