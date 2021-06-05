package com.lid.dailydoc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = REPLACE)
    suspend fun insertNote(note: Note)

    @Insert(onConflict = REPLACE)
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM notes_table WHERE note_id = :noteId")
    fun observeNoteById(noteId: String): Flow<Note>

    @Query("SELECT * FROM notes_table WHERE note_id = :noteId")
    suspend fun getNoteById(noteId: String): Note?

//    @Query("SELECT EXISTS (SELECT 1 FROM notes_table WHERE note_date = :date)")
//    fun exists(date: String): Flow<Boolean>

    @Query("SELECT EXISTS (SELECT 1 FROM notes_table WHERE note_date = :date)")
    fun noteExists(date: Long): Flow<Boolean>

    @Query("SELECT * FROM notes_table WHERE note_date = :date")
    fun getNoteByDate(date: Long): Note



    @Query("DELETE FROM notes_table WHERE note_date = :date")
    fun deleteNote(date: Long)

    @Query("SELECT * FROM notes_table WHERE isSynced = 0")
    suspend fun getAllUnsyncedNotes(): List<Note>

    @Query("DELETE FROM notes_table WHERE isSynced = 1")
    suspend fun deleteAllSyncedNotes()

}