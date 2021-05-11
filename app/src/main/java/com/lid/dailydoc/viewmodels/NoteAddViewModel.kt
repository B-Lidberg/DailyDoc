package com.lid.dailydoc.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.*
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepository
import com.lid.dailydoc.utils.getCurrentDateAsString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class NoteAddViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    // DATE
    @RequiresApi(Build.VERSION_CODES.O)
    var date: String = getCurrentDateAsString()

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

    // get or create Daily Note
    @RequiresApi(Build.VERSION_CODES.O)
    fun setDate() {
        date = getCurrentDateAsString()
    }

    private var noteExists: Boolean = false

    fun checkNoteExists() {
        noteExists = repository.exists(date).asLiveData().value == true
    }

    var cachedNote = Note(dateCreated = "Mon, Jan 15, 2001")

    @ObsoleteCoroutinesApi
    fun cacheNote() {
        viewModelScope.launch(Dispatchers.Default) {
            checkNoteExists()
            cachedNote = if (noteExists) getNote() else Note(date)
        }
    }

    private fun getNote(): Note = repository.findNoteByDate(date)

    fun getNoteById(noteId: Long): Note {
        return runBlocking(Dispatchers.IO) {
            repository.findNoteById(noteId)
        }
    }

    // Add Note
    @ObsoleteCoroutinesApi
    fun addNote(note: Note) {
        viewModelScope.launch(newSingleThreadContext("insert note")) {
            if (noteExists) repository.updateNote(note) else repository.insertNote(note)
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
