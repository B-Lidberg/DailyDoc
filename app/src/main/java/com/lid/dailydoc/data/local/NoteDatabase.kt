package com.lid.dailydoc.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lid.dailydoc.data.extras.fakeNote
import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    private class NoteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.noteDao())
                }
            }
        }

        suspend fun populateDatabase(noteDao: NoteDao) {
            noteDao.insertNote(fakeNote)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope,
        ) : NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                 .addCallback(NoteDatabaseCallback(scope))
                 .build()
                INSTANCE = instance
                instance
            }
        }
    }
}