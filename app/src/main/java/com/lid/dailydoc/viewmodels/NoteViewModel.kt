package com.lid.dailydoc.viewmodels

import androidx.lifecycle.*
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepositoryImpl
import com.lid.dailydoc.other.Event
import com.lid.dailydoc.other.Resource
import com.lid.dailydoc.utils.getCurrentDateAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepositoryImpl
) : ViewModel() {

    init {
        viewModelScope.launch {
            checkForCurrentNote()
        }
    }


    private val _forceUpdate = MutableLiveData<Boolean>(false)
    val forceUpdate: LiveData<Boolean> = _forceUpdate

    fun syncAllNotes() = _forceUpdate.postValue(true)

    fun clearLocalDatabase() {
        viewModelScope.launch {
            repository.clearLocalDatabase()
        }
    }


    private val _allNotes = _forceUpdate.switchMap {
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
            val note = _currentNote?.value ?: Note(date)
            if (note.date != date) {
                _currentNote.postValue(Note(date))
            }
        }
    }

    fun getCurrentNote(): Note {
        return runBlocking(Dispatchers.IO) {
            repository.findNoteByDate(getCurrentDateAsLong())
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