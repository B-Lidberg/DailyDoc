package com.lid.dailydoc.data.repository

import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    val allNotes: Flow<List<Note>>

    suspend fun insertNote(note: Note)

    fun findNoteById(noteId: Long): Note

    fun exists(date: String): Flow<Boolean>

    fun noteExists(date: String): Boolean

    fun findNoteByDate(date: String): Note

    suspend fun updateNote(note: Note)
}