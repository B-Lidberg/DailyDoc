package com.lid.dailydoc.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.navigation.DrawerNavigation
import com.lid.dailydoc.other.Status
import com.lid.dailydoc.presentation.components.CustomTopBar
import com.lid.dailydoc.presentation.components.NoteCard
import com.lid.dailydoc.utils.getCurrentDateAsLong
import com.lid.dailydoc.viewmodels.NoteViewModel
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.launch


@Composable
fun NoteListScreen(
    vm: NoteViewModel = viewModel(),
    userVm: UserViewModel = viewModel(),
    toDetails: (String) -> Unit,
    toAdd: (Note) -> Unit,
    currentNote: Note,
) {
    val currentNotes = rememberSaveable() { mutableStateOf<List<Note>>(emptyList()) }
    val exists by vm.exists.observeAsState(false)
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val currentLifeCycle = LocalLifecycleOwner.current

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerNavigation(userVm, { vm.syncAllNotes() }, { vm.clearLocalDatabase() } ) },
        drawerBackgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.95f),
        drawerElevation = 8.dp,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { NoteListTopBar() },
            floatingActionButton = { AddNoteButton(toAdd, currentNote, exists) },
            content = {
                vm.allNotes.observe(currentLifeCycle, Observer {
                    it?.let { event ->
                        val result = event.peekContent()
                        when (result.status) {
                            Status.SUCCESS -> {
                                currentNotes.value = result.data!!.sortedByDescending { note -> note.date }
                            }
                            Status.ERROR -> {
                                event.getContentIfNotHandled()?.let { errorResource ->
                                    scope.launch {
                                        errorResource.message?.let { message ->
                                            scaffoldState.snackbarHostState.showSnackbar(message)
                                        }
                                    }
                                }
                                result.data?.let { notes ->
                                    currentNotes.value = notes.sortedByDescending { note -> note.date }
                                }
                            }
                            Status.LOADING -> {
                                result.data?.let { notes ->
                                    currentNotes.value = notes.sortedByDescending { note -> note.date }
                                }
                            }
                        }
                    }
                })
                NoteList(currentNotes.value, toDetails, { vm.checkForCurrentNote() } )
            }
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


@Composable
fun NoteList(notes: List<Note>, toDetails: (String) -> Unit, checkForCurrentNote: () -> Unit) {
    val date = getCurrentDateAsLong()
    LazyColumn {
        items(items = notes) { note ->
            NoteCard(note, toDetails)
            if (note.date == date) checkForCurrentNote()
        }
    }
}

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

