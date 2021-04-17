package com.lid.dailydoc.presentation.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.extras.surveyQuestions
import com.lid.dailydoc.presentation.components.CustomTopBar
import com.lid.dailydoc.presentation.viewmodels.NoteDetailViewModel

@Composable
fun NoteDetailScreen(vm: NoteDetailViewModel, noteId: Long) {
    val note = vm.getNote(noteId)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                modifier = Modifier.padding(bottom = 6.dp, start = 8.dp, end = 8.dp)
            ) {
                CustomTopBar(note.dateCreated) { EditBodyButton() }
            }
        },
        content = {
            TempBody(note)
        }
    )
}

@Composable
fun TempBody(note: Note) {
    Column {
        LazyColumn {
            item { Text(text = "Note ID:\n${note.id}\n", fontSize = 24.sp) }
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
@Composable
fun EditBodyButton() {
    IconButton(onClick = {} ) { Icon(Icons.Default.Edit, contentDescription = "Search Icon") }
}
