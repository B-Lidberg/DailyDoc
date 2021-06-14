package com.lid.dailydoc.data.repository

import android.app.Application
import androidx.annotation.WorkerThread
import com.lid.dailydoc.data.extras.fakeNote
import com.lid.dailydoc.data.local.NoteDao
import com.lid.dailydoc.data.model.LocallyDeletedNoteId
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.remote.NoteApi
import com.lid.dailydoc.data.remote.requests.DeleteNoteRequest
import com.lid.dailydoc.other.Resource
import com.lid.dailydoc.other.checkForInternetConnection
import com.lid.dailydoc.other.networkBoundResource
import com.lid.dailydoc.utils.getCurrentDateAsLong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val noteApi: NoteApi,
    private val context: Application
) : NoteRepository {

    private var curNotesResponse: Response<List<Note>>? = null

    suspend fun deleteLocallyDeletedNoteId(deletedNoteID: String) {
        noteDao.deleteLocallyNoteId(deletedNoteID)
    }

    suspend fun deleteNote(noteId: String) {
        val response = try {
            noteApi.deleteNote(DeleteNoteRequest(noteId))
        } catch (e: java.lang.Exception) {
            null
        }
        noteDao.deleteNoteById(noteId)
        if (response == null || !response.isSuccessful) {
            noteDao.insertLocallyDeletedNoteId(LocallyDeletedNoteId(noteId))
        } else {
            deleteLocallyDeletedNoteId(noteId)
        }
    }

    suspend fun clearLocalDatabase() {
        noteDao.deleteAllNotes()
    }

    suspend fun syncNotes() {
        val locallyDeletedNoteIDs = noteDao.getAllLocallyDeletedNoteIds()
        locallyDeletedNoteIDs.forEach { id -> deleteNote(id.deletedNoteId) }

        val unsyncedNotes = noteDao.getAllUnsyncedNotes()
        unsyncedNotes.forEach { note -> insertNote(note) }

        curNotesResponse = noteApi.getNotes()
        curNotesResponse?.body()?.let { notes ->
            noteDao.deleteAllNotes()
            insertNotes(notes.onEach { note -> note.isSynced = true } )
        }
    }

    override fun getAllNotes(): Flow<Resource<List<Note>>> {
        return networkBoundResource(
            query = {
                noteDao.getAllNotes()
            },
            fetch = {
                syncNotes()
                curNotesResponse
            },
            saveFetchResult = { response ->
                response?.body()?.let { notes ->
                    insertNotes(notes)
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
    }

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

    override suspend fun insertNotes(notes: List<Note>) {
        notes.forEach { note -> insertNote(note) }
    }

    override suspend fun getNoteById(noteId: String) = noteDao.getNoteById(noteId)

    override fun noteExists(date: Long): Flow<Boolean> {
        return noteDao.noteExists(date)
    }


    override fun findNoteByDate(date: Long): Note {
        return noteDao.getNoteByDate(date) ?: Note(getCurrentDateAsLong())
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