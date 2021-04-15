package com.lid.dailydoc.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.presentation.viewmodels.NoteViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.presentation.components.NoteCard
import com.lid.dailydoc.presentation.components.CustomTopBar
import com.lid.dailydoc.presentation.viewmodels.NoteAddViewModel
import com.lid.dailydoc.utils.getCurrentDateAsString


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteListScreen(vm: NoteViewModel, toDetails: (Long) -> Unit, toAdd: (Note) -> Unit, note: Note) {
    val notes by vm.allNotes.observeAsState(emptyList())
    Scaffold(
        topBar = { NoteListTopBar(vm) },
        floatingActionButton = { AddNoteButton(toAdd, note) },
        content = { NoteList(notes, toDetails) }
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
//        Row {
//            Text(
//                text = "Room Note Sample",
//                modifier = Modifier.weight(1f),
//                style = MaterialTheme.typography.h5
//            )
//            CustomButton(clearNotes, "Clear All")
//        }

    }
}

@Composable
fun NoteList(notes: List<Note>, toDetails: (Long) -> Unit) {
    LazyColumn {
        items(items = notes) { note ->
            NoteCard(note, toDetails)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNoteButton(toAdd: (Note) -> Unit, note: Note) {
    val buttonText = if (note.dateCreated == getCurrentDateAsString()) {
        "Let's Edit Your Daily Note!"
    } else {
        "Let's Add Your Daily Note!"
    }
    ExtendedFloatingActionButton(
        text = {
            Text(
                text = buttonText
            )
        },
        onClick = { toAdd(note) },
        icon = { if (note.dateCreated == getCurrentDateAsString()) {
                    Icon(Icons.Outlined.Edit, contentDescription = "Edit Button")
                } else {
                    Icon(Icons.Outlined.Star, contentDescription = "Add Button")
                }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}