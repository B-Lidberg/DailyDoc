package com.lid.dailydoc.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.repository.NoteRepositoryImpl
import com.lid.dailydoc.utils.getCurrentDateAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    repository: NoteRepositoryImpl
) : ViewModel() {

    val allNotes: Flow<List<Note>> = repository.allNotes

    @RequiresApi(Build.VERSION_CODES.O)
    var date: Long = getCurrentDateAsLong()

    val exists: LiveData<Boolean> = repository.noteExists(date).asLiveData()

}