package com.lid.dailydoc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lid.dailydoc.data.model.LocallyDeletedNoteId
import com.lid.dailydoc.data.model.Note

@Database(
    version = 1,
    entities = [Note::class, LocallyDeletedNoteId::class],
)

@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}