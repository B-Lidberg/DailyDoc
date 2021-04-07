package com.lid.dailydoc.presentation.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.surveyQuestions
import com.lid.dailydoc.presentation.components.DateBar
import com.lid.dailydoc.presentation.viewmodels.NoteDetailViewModel

@Composable
fun NoteDetailScreen(vm: NoteDetailViewModel, noteId: Int) {
    val note = vm.getNote(noteId)

    Scaffold(
        topBar = { DateBar(note.dateCreated) },
        content = {
            TempBody(note)
        }
    )
}

@Composable
fun TempBody(note: Note) {
    Column {
        LazyColumn {
            item { SurveyDetails(note) }
            item { Text(text = "Summary:\n${note.summary}\n", fontSize = 24.sp) }
            item { Text(text = "Body:\n${note.body}\n", fontSize = 24.sp) }

        }
    }
}

@Composable
fun SurveyDetails(note: Note) {
    Column {
        Text(text = "${surveyQuestions[0]}:\n ${note.survey1}\n", fontSize = 24.sp)
        Text(text = "${surveyQuestions[1]}:\n ${note.survey2}\n", fontSize = 24.sp)
        Text(text = "${surveyQuestions[2]}:\n ${note.survey3}\n", fontSize = 24.sp)
    }
}
