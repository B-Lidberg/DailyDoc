package com.lid.dailydoc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = IGNORE)
    suspend fun insertNote(note: Note)

    @Query("DELETE FROM notes_table WHERE note_date = :date")
    fun deleteNote(date: String)

    @Query("SELECT * FROM notes_table WHERE note_id = :noteId")
    fun findById(noteId: Long): Note

    @Query("SELECT EXISTS (SELECT 1 FROM notes_table WHERE note_date = :date)")
    fun exists(date: String): Flow<Boolean>

    @Query("SELECT * FROM notes_table WHERE note_date = :date")
    fun getNoteByDate(date: String): Note

    @Insert(onConflict = REPLACE)
    suspend fun updateNote(note: Note)

}