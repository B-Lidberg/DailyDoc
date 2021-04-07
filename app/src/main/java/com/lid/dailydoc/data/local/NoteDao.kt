package com.lid.dailydoc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): Flow<List<Note>>

    @Insert
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM notes_table WHERE note_id = :noteId")
    fun findById(noteId: Int): Note

    @Query("DELETE FROM notes_table")
    suspend fun clearNotes()

}