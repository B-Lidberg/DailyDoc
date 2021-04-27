package com.lid.dailydoc.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.navigation.DrawerNavigation
import com.lid.dailydoc.presentation.components.CustomTopBar
import com.lid.dailydoc.presentation.components.NoteCard
import com.lid.dailydoc.viewmodels.NoteViewModel
import com.lid.dailydoc.viewmodels.UserViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteListScreen(
    vm: NoteViewModel,
    userVm: UserViewModel,
    toDetails: (Long) -> Unit,
    toAdd: (Note) -> Unit,
    note: Note,
) {
    val notes by vm.allNotes.collectAsState(emptyList())
    val exists by vm.exists.observeAsState(false)
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerNavigation(userVm) },
        drawerBackgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.95f),
        drawerElevation = 8.dp,
    ) {
        Scaffold(
            topBar = { NoteListTopBar() },
            floatingActionButton = { AddNoteButton(toAdd, note, exists) },
            content = { NoteList(notes, toDetails) }
        )
    }
}

@Composable
fun NoteListTopBar() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier.padding(bottom = 6.dp, start = 8.dp, end = 8.dp)
    ) {
        CustomTopBar("Daily Doc") { }
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
        backgroundColor = MaterialTheme.colors.secondary,
    ) {
        Icon(
            if (exists) Icons.Outlined.Edit else Icons.Outlined.Add,
            contentDescription = "To Add Screen"
        )
    }
}
