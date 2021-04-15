package com.lid.dailydoc.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.presentation.viewmodels.NoteViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.presentation.components.NoteCard
import com.lid.dailydoc.presentation.components.CustomTopBar
import com.lid.dailydoc.presentation.viewmodels.NoteAddViewModel
import com.lid.dailydoc.utils.getCurrentDateAsString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteListScreen(vm: NoteViewModel, toDetails: (Long) -> Unit, toAdd: (Note) -> Unit, note: Note) {
    val notes by vm.allNotes.observeAsState(emptyList())
    val exists by vm.exists.observeAsState(false)
    Scaffold(
        topBar = { NoteListTopBar(vm) },
        floatingActionButton = { AddNoteButton(toAdd, note, exists) },
        content = {
            NoteList(notes, toDetails, exists) }
    )
}

@Composable
fun NoteListTopBar(vm: NoteViewModel) {
    val clearNotes: () -> Unit = { vm.clearNotes() }
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier.padding(bottom = 6.dp, start = 8.dp, end = 8.dp)
    ) {
        CustomTopBar("Room Note Sample", {}, Icons.Default.Menu)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteList(notes: List<Note>, toDetails: (Long) -> Unit, exists: Boolean) {
    LazyColumn {
        items(items = notes) { note ->
            NoteCard(note, toDetails, exists)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNoteButton(toAdd: (Note) -> Unit, note: Note, exists: Boolean) {
    val buttonText = if (exists) {
        ""
    } else {
        "Let's Add Your Daily Note!"
    }
    ExtendedFloatingActionButton(
        modifier = Modifier.animateContentSize(),
        text = {
            Text(
                text = buttonText
            )
        },
        onClick = {
            toAdd(note)
            println("NOTE: id: ${note.id}, date: ${note.dateCreated}, summary: ${note.summary}, body: ${note.body}")
                  },
        icon = { if (exists) {
                    Icon(Icons.Outlined.Edit, contentDescription = "Edit Button")
                } else {
                    Icon(Icons.Outlined.Star, contentDescription = "Add Button")
                }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}