package com.lid.dailydoc.data.repository

import androidx.annotation.WorkerThread
import com.lid.dailydoc.data.extras.fakeNote
import com.lid.dailydoc.data.local.NoteDao
import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) : NoteRepository {
    init {
        CoroutineScope(IO).launch { noteDao.insertNote(fakeNote) }
    }

    override val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override fun findNoteById(noteId: Long) = noteDao.findById(noteId)

    override fun exists(date: String): Flow<Boolean> {
        return noteDao.exists(date)
    }

    override fun noteExists(date: String): Boolean {
        return noteDao.noteExists(date)
    }

    override fun findNoteByDate(date: String): Note {
        return noteDao.getNoteByDate(date)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }
}