package com.lid.dailydoc.viewmodels

import androidx.lifecycle.*
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepositoryImpl
import com.lid.dailydoc.other.Event
import com.lid.dailydoc.other.Resource
import com.lid.dailydoc.utils.getCurrentDateAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepositoryImpl,
) : ViewModel() {

    private val forceUpdate = MutableLiveData<Boolean>(false)

    fun syncAllNotes() = forceUpdate.postValue(true)

    private val _allNotes = forceUpdate.switchMap {
        repository.getAllNotes().asLiveData(viewModelScope.coroutineContext)
            .switchMap {
                MutableLiveData(Event(it))
            }
    }
    val allNotes: LiveData<Event<Resource<List<Note>>>> = _allNotes


    private val _currentNote: MutableLiveData<Note> = MutableLiveData()
    val currentNote: LiveData<Note> = _currentNote

    fun checkForCurrentNote() {
        viewModelScope.launch {
            val date = getCurrentDateAsLong()
            val note = repository.getNoteByDate(date) ?: Note(date)
            _currentNote.postValue(note)
        }
    }

    fun getCurrentNote(): Note {
        return runBlocking(Dispatchers.IO) {
            repository.getNoteByDate(getCurrentDateAsLong())
        }
    }

    fun cacheNote(note: Note) {
        viewModelScope.launch {
            if (note.date == getCurrentDateAsLong()) {
                _currentNote.postValue(note)
            }
        }
    }

    val exists: LiveData<Boolean> = repository.noteExists(getCurrentDateAsLong()).asLiveData()

}