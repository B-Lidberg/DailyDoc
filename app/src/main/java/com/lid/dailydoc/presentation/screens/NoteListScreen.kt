package com.lid.dailydoc.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.viewmodels.NoteViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.presentation.components.NoteCard
import com.lid.dailydoc.presentation.components.CustomTopBar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteListScreen(
    vm: NoteViewModel,
    toDetails: (Long) -> Unit,
    toAdd: (Note) -> Unit,
    note: Note
) {
    val notes by vm.allNotes.collectAsState(emptyList())
    val exists by vm.exists.observeAsState(false)

    Scaffold(
        topBar = { NoteListTopBar() },
        floatingActionButton = { AddNoteButton(toAdd, note, exists) },
        content = {
            NoteList(notes, toDetails) }
    )
}

@Composable
fun NoteListTopBar() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier.padding(bottom = 6.dp, start = 8.dp, end = 8.dp)
    ) {
        CustomTopBar("Daily Doc") { SearchButton() }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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
fun AddNoteButton(toAdd: (Note) -> Unit, note: Note, exists: Boolean) {
    FloatingActionButton(
        onClick = { toAdd(note) },
        backgroundColor = MaterialTheme.colors.secondaryVariant,

    ) {
        Icon(imageVector =  if (exists) Icons.Outlined.Edit else Icons.Outlined.Add, contentDescription = "To Add Screen")
    }
}

@Composable
fun SearchButton() {
    IconButton(onClick = {} ) { Icon(Icons.Default.Search, contentDescription = "Search Icon") }
}