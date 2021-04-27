package com.lid.dailydoc.viewmodels

import androidx.lifecycle.ViewModel
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    fun getNote(noteId: Long): Note {
        return runBlocking(Dispatchers.IO) {
            repository.findNoteById(noteId)
        }
    }

}
