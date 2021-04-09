package com.lid.dailydoc.presentation.viewmodels

import androidx.lifecycle.*
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepository
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()

    fun clearNotes() = viewModelScope.launch {
        repository.clearNotes()
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