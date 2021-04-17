package com.lid.dailydoc.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepository
import com.lid.dailydoc.utils.getCurrentDateAsString
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalArgumentException

class NoteViewModel(repository: NoteRepository) : ViewModel() {

    val allNotes: Flow<List<Note>> = repository.allNotes

    @RequiresApi(Build.VERSION_CODES.O)
    var date: String = getCurrentDateAsString()

    val exists: LiveData<Boolean> = repository.exists(date).asLiveData()

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