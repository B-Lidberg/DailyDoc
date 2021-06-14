package com.lid.dailydoc.presentation.screens


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lid.dailydoc.data.extras.surveyQuestions
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.presentation.components.CustomTopBar
import com.lid.dailydoc.utils.getDateAsString
import com.lid.dailydoc.utils.getNoteInfo
import com.lid.dailydoc.viewmodels.NoteDetailViewModel

@Composable
fun NoteDetailScreen(
    vm: NoteDetailViewModel,
    noteId: String,
) {
    val note = vm.getNote(noteId)
    val headerDate = getDateAsString(note.date)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                modifier = Modifier.padding(bottom = 6.dp, start = 8.dp, end = 8.dp)
            ) {
                CustomTopBar(headerDate) { ShareNoteButton(note) }
            }
        },
        content = {
            TempBody(note)
        }
    )
}

@Composable
fun TempBody(note: Note) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { Text(text = "Owner:\n${note.owner}\n", fontSize = 24.sp) }

        item { Text(text = "Note ID:\n${note.noteId}\n", fontSize = 24.sp) }
        item { Text(text = "DATE:\n${note.date}\n") }
        item { SurveyDetails(note) }
        item { Text(text = "Summary:\n${note.summary}\n", fontSize = 24.sp) }
        item { Text(text = "Body:\n${note.content}\n", fontSize = 24.sp) }
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
fun ShareNoteButton(note: Note) {
    val context = LocalContext.current
    IconButton(
        onClick = { shareNote(note, context)}
    ) {
        Icon(Icons.Default.Share,
            contentDescription = "Share Note"
        )
    }
}

private fun shareNote(note: Note, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "DailyDoc: ${note.date}")
        putExtra(Intent.EXTRA_TEXT, getNoteInfo(note))
    }
    context.startActivity(Intent.createChooser(intent, "Share note"))
}

