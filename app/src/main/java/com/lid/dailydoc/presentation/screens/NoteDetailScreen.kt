package com.lid.dailydoc.presentation.screens


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lid.dailydoc.data.extras.surveyQuestions
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.presentation.components.CustomTopBar
import com.lid.dailydoc.utils.getDateAsString
import com.lid.dailydoc.utils.getNoteInfo
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun NoteDetailScreen(
    getNote: () -> Note,
) {
    val note = getNote.invoke()
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
            NoteDetails(note)
        }
    )
}

@Composable
fun NoteDetails(note: Note) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Box() {
                Text(
                    text = note.summary,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                )
            }
        }
        item { Divider(Modifier.fillMaxWidth(.85f).height(2.dp)) }
        item {
            Box {
                MarkdownText(
                    markdown = note.content,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
                )
            }
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

@Preview
@Composable
fun ComposablePreview() {
    val note = Note(date = 18850, summary = "A note with markdown text and a bunch of random text to fill the screen as well!",
        content =
        """# Markdown Text & Preview added 

* Markdown put into NoteDetailScreen 
* Preview added for screen as well 

*This should be italicized* 
**This should be bold** 
***This should be I and B***

x
y
z"""
        )
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
            NoteDetails(note)
        }
    )
}

