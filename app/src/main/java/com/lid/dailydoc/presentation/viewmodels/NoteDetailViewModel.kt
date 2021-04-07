package com.lid.dailydoc.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException

class NoteDetailViewModel (private val repository: NoteRepository) : ViewModel() {

    fun getNote(noteId: Int): Note {
        return runBlocking(Dispatchers.IO) {
            repository.findNoteById(noteId)
        }
    }
}
class NoteDetailViewModeFactory(
    private val repository: NoteRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown DetailViewModel class")
    }
}