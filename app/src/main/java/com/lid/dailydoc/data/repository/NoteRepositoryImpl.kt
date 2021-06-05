package com.lid.dailydoc.data.repository

import android.app.Application
import androidx.annotation.WorkerThread
import com.lid.dailydoc.data.extras.fakeNote
import com.lid.dailydoc.data.local.NoteDao
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.remote.NoteApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val noteApi: NoteApi,
    private val context: Application
) : NoteRepository {
    init {
        CoroutineScope(IO).launch { noteDao.insertNote(fakeNote) }
    }

    override val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insertNote(note: Note) {
        val response = try {
            noteApi.addNote(note)
        } catch (e: Exception) {
            null
        }
        if (response != null && response.isSuccessful) {
            noteDao.insertNote(note.apply { isSynced = true } )
        } else {
            noteDao.insertNote(note)
        }
    }

    override suspend fun getNoteById(noteId: String) = noteDao.getNoteById(noteId)

    override fun noteExists(date: Long): Flow<Boolean> {
        return noteDao.noteExists(date)
    }


    override fun findNoteByDate(date: Long): Note {
        return noteDao.getNoteByDate(date)
    }

    override suspend fun getAllUnsyncedNotes(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllSyncedNotes() {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(note: Note) {
        val response = try {
            noteApi.addNote(note)
        } catch (e: Exception) {
            null
        }
        if (response != null && response.isSuccessful) {
            noteDao.updateNote(note.apply { isSynced = true } )
        } else {
            noteDao.updateNote(note)
        }
    }

    override fun observeNoteById(noteId: String): Flow<Note> {
        return noteDao.observeNoteById(noteId)
    }
}