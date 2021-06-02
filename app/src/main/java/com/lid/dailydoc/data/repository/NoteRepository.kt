package com.lid.dailydoc.data.repository

import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    val allNotes: Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    fun observeNoteById(noteId: String): Flow<Note>

    suspend fun getNoteById(noteId: String): Note?

    fun noteExists(date: Long): Flow<Boolean>

    fun findNoteByDate(date: Long): Note


    suspend fun getAllUnsyncedNotes(): List<Note>

    suspend fun deleteAllSyncedNotes()
}