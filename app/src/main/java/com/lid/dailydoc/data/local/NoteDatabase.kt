package com.lid.dailydoc.data.local

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.lid.dailydoc.data.model.Note

@Database(
    version = 1,
    entities = [Note::class],
)

@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}