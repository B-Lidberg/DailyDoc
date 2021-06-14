package com.lid.dailydoc.data.repository

import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.other.Resource
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<Resource<List<Note>>>

    suspend fun insertNote(note: Note)

    suspend fun insertNotes(notes: List<Note>)

    suspend fun updateNote(note: Note)

    fun observeNoteById(noteId: String): Flow<Note>

    suspend fun getNoteById(noteId: String): Note?

    fun noteExists(date: Long): Flow<Boolean>

    suspend fun getNoteByDate(date: Long): Note?


    suspend fun getAllUnsyncedNotes(): List<Note>

    suspend fun deleteAllSyncedNotes()
}