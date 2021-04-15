package com.lid.dailydoc.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = IGNORE)
    suspend fun insertNote(note: Note)

    // Unsure if I'll use @Transaction or @Update
//    @Transaction
//    suspend fun updateNote(note: Note) {
//        deleteNote(note.dateCreated)
//        insertNote(note)
//    }

    @Query("DELETE FROM notes_table WHERE note_date = :date")
    fun deleteNote(date: String)

    @Query("SELECT * FROM notes_table WHERE note_id = :noteId")
    fun findById(noteId: Long): Note

    @Query("SELECT EXISTS (SELECT 1 FROM notes_table WHERE note_date = :date)")
    fun exists(date: String): Boolean

    @Query("SELECT * FROM notes_table WHERE note_date = :date")
    fun getNoteByDate(date: String): Note

    @Insert(onConflict = REPLACE)
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM notes_table")
    suspend fun clearNotes()



}