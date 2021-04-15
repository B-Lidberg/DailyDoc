package com.lid.dailydoc.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepository
import com.lid.dailydoc.utils.getCurrentDateAsString
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()

    fun clearNotes() = viewModelScope.launch {
        repository.clearNotes()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    var date: String = getCurrentDateAsString(

    )
    @RequiresApi(Build.VERSION_CODES.O)
    fun setDate() {
        date = getCurrentDateAsString()
    }

    var noteExists: Boolean = false
    var exists: LiveData<Boolean> = repository.exists(date).asLiveData()

    fun checkNoteExists() {
        noteExists = repository.noteExists(date)
    }
    var cachedNote = Note(dateCreated = "Mon, Jan 15, 2001")

    private fun getNote(): Note = repository.findNoteByDate(date)

    fun cacheNote() {
        viewModelScope.launch(Dispatchers.Default) {
            checkNoteExists()
            cachedNote = if (exists.value == true) getNote() else Note(date)
        }
    }
}
    class NoteViewModelFactory(
        private val repository: NoteRepository,
        ) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown NoteViewModel class")
        }

}