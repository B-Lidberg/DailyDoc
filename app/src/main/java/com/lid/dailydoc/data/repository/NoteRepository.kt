package com.lid.dailydoc.data.repository

import androidx.annotation.WorkerThread
import com.lid.dailydoc.data.local.NoteDao
import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.*

class NoteRepository(
    private val noteDao: NoteDao,
) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    fun findNoteById(noteId: Long) = noteDao.findById(noteId)


    suspend fun clearNotes() {
        noteDao.clearNotes()
    }
}