package com.lid.dailydoc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lid.dailydoc.data.model.LocallyDeletedNoteId
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
    fun getNoteById(noteId: String): Note?

    @Query("SELECT EXISTS (SELECT 1 FROM notes_table WHERE note_date = :date)")
    fun noteExists(date: Long): Flow<Boolean>

    @Query("SELECT * FROM notes_table WHERE note_date = :date")
    suspend fun getNoteByDate(date: Long): Note?


    @Query("DELETE FROM notes_table WHERE note_date = :date")
    fun deleteNote(date: Long)

    @Query("DELETE FROM notes_table WHERE note_id = :noteId")
    suspend fun deleteNoteById(noteId: String)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes_table WHERE isSynced = 0")
    suspend fun getAllUnsyncedNotes(): List<Note>

    @Query("DELETE FROM notes_table WHERE isSynced = 1")
    suspend fun deleteAllSyncedNotes()

    @Query("SELECT * FROM locally_deleted_note_ids")
    suspend fun getAllLocallyDeletedNoteIds(): List<LocallyDeletedNoteId>

    @Query("DELETE FROM locally_deleted_note_ids WHERE deletedNoteID = :deletedNoteID")
    suspend fun deleteLocallyNoteId(deletedNoteID: String)

    @Insert(onConflict = REPLACE)
    suspend fun insertLocallyDeletedNoteId(locallyDeletedNoteID: LocallyDeletedNoteId)

}