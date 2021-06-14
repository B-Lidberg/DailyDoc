package com.lid.dailydoc.viewmodels

import androidx.lifecycle.*
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepositoryImpl
import com.lid.dailydoc.data.repository.UserDataRepository
import com.lid.dailydoc.utils.getCurrentDateAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NoteAddViewModel @Inject constructor(
    private val noteRepository: NoteRepositoryImpl,
    userRepository: UserDataRepository
) : ViewModel() {

//    private val _note = MutableLiveData<Event<Resource<Note>>>()
//    val note: LiveData<Event<Resource<Note>>> = _note

    private val userDataFlow = userRepository.userDataFlow

    val userData = userDataFlow.asLiveData()

    val currentUsername = userDataFlow.map { user ->
        user.username
    }.asLiveData()

    fun insertNote(note: Note) = GlobalScope.launch {
        noteRepository.insertNote(note)
    }

//    fun getNoteById(noteId: String) = viewModelScope.launch {
//        _note.postValue(Event(Resource.loading(null)))
//        val note = noteRepository.getNoteById(noteId)
//        note?.let { currentNote ->
//            if (currentNote.date == getCurrentDateAsLong()) {
//                _note.postValue(Event(Resource.success(currentNote)))
//            } else {
//                val newNote = Note(date = getCurrentDateAsLong())
//                _note.postValue(Event(Resource.success(newNote)))
//            }
//        } ?: _note.postValue(Event(Resource.success(Note(getCurrentDateAsLong()))))
//        // _note.postValue(Event(Resource.error("Note not found", null)))
//    }
//
//    fun saveNote() {
//        val authUser = currentUsername.value ?: ""
//
//
//    }
//
//    // DATE
//    @RequiresApi(Build.VERSION_CODES.O)
//    var date: Long = getCurrentDateAsLong()

    // SUMMARY
    private val _summary: MutableLiveData<String> = MutableLiveData()
    val summary: LiveData<String> = _summary

    fun onSummaryChange(newSummary: String) {
        _summary.value = newSummary
    }

    // BODY
    private val _body: MutableLiveData<String> = MutableLiveData()
    val body: LiveData<String> = _body

    fun onBodyChange(newBody: String) {
        _body.value = newBody
    }

    // SURVEYS
    private val _survey1: MutableLiveData<String> = MutableLiveData()
    val survey1: LiveData<String> = _survey1

    fun onSurvey1Change(newAnswer: String) {
        _survey1.value = newAnswer
    }

    private val _survey2: MutableLiveData<String> = MutableLiveData()
    val survey2: LiveData<String> = _survey2

    fun onSurvey2Change(newAnswer: String) {
        _survey2.value = newAnswer
    }

    private val _survey3: MutableLiveData<String> = MutableLiveData()
    val survey3: LiveData<String> = _survey3

    fun onSurvey3Change(newAnswer: String) {
        _survey3.value = newAnswer
    }


//    @ObsoleteCoroutinesApi
//    fun cacheNote() {
//        viewModelScope.launch(Dispatchers.Default) {
//            noteExists = noteRepository.noteExists(date).asLiveData().value ?: false
//            cachedNote = if (noteExists) getNote() else Note(date)
//        }
//    }


    fun getNoteById(noteId: String): Note {
        return runBlocking(Dispatchers.IO) {
            noteRepository.getNoteById(noteId)
        } ?: Note(getCurrentDateAsLong(), owner = currentUsername.value ?: "")
    }

//    fun getCurrentNote(): Note = noteRepository.findNoteByDate(getCurrentDateAsLong())

    suspend fun getCurrentNote(): Note {
        var result : Note? = null
        var waitFor = viewModelScope.async {
            result = noteRepository.getNoteByDate(getCurrentDateAsLong())
            return@async result!!
        }
        waitFor.await()
        return result!!
    }


    // Add Note
    @ObsoleteCoroutinesApi
    fun addNote(note: Note) {
        GlobalScope.launch {
            noteRepository.insertNote(note)
        }
    }

    fun clearNote() {
        onSummaryChange("")
        onBodyChange("")
        onSurvey1Change("")
        onSurvey2Change("")
        onSurvey3Change("")
    }
}
