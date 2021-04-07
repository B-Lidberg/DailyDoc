package com.lid.dailydoc

import android.app.Application
import com.lid.dailydoc.data.local.NoteDatabase
import com.lid.dailydoc.data.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { NoteDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}