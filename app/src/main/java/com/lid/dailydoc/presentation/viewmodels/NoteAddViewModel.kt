package com.lid.dailydoc.presentation.viewmodels

import androidx.lifecycle.*
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class NoteAddViewModel(private val repository: NoteRepository) : ViewModel() {

    // DATE
    private val _date: MutableLiveData<String> = MutableLiveData()
    val date: LiveData<String> = _date

    fun onDateChange(newDate: String) {
        _date.value = newDate
    }

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

    // Add Note
    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }
}


class NoteAddViewModeFactory(
    private val repository: NoteRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteAddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteAddViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown AddViewModel class")
    }
}
