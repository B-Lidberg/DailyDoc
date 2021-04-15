package com.lid.dailydoc

import android.os.Build
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.lid.dailydoc.MainDestinations.NOTES
import com.lid.dailydoc.MainDestinations.NOTE_DETAILS
import com.lid.dailydoc.MainDestinations.NOTE_ID
import com.lid.dailydoc.MainDestinations.NOTE_KEY
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.presentation.screens.NoteAddScreen
import com.lid.dailydoc.presentation.screens.NoteDetailScreen
import com.lid.dailydoc.presentation.screens.NoteListScreen
import com.lid.dailydoc.presentation.viewmodels.*
import kotlinx.coroutines.*

object MainDestinations {
    const val NOTES = "notes"
    const val NOTE_DETAILS = "note_details"
    const val NOTE_ID = "noteId"
    const val NOTE_KEY = "note"
}

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    NoteListVm: NoteViewModel,
    addNoteVm: NoteAddViewModel,
    startDestination: String = NOTES,
) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = NOTES,
        ) {
            addNoteVm.setDate()
            addNoteVm.cacheNote()
            val note = addNoteVm.cachedNote
            NoteListScreen(NoteListVm, actions.detailScreen, actions.addScreen, note)
        }
        val noteId = navController.previousBackStackEntry?.arguments?.getLong(NOTE_ID)
        val note = noteId?.let { addNoteVm.getNoteById(it) }
        if (noteId != null) {
            composable("$NOTE_KEY/${NOTE_ID}") { NoteAddScreen(addNoteVm, actions.upPress, note!!) }
        } else {
            composable("$NOTE_KEY/${NOTE_ID}") { NoteAddScreen(addNoteVm, actions.upPress, addNoteVm.cachedNote) }
        }

        composable("$NOTE_DETAILS/${NOTE_ID}") {
            val detailVm: NoteDetailViewModel = viewModel(
                factory = NoteDetailViewModeFactory(NotesApplication().repository))

            val noteId = navController.previousBackStackEntry?.arguments?.getLong(NOTE_ID)
            if (noteId != null) NoteDetailScreen(detailVm, noteId)
        }
    }
}

class MainActions(navController: NavController) {

    val detailScreen: (Long) -> Unit = { noteId: Long ->
        navController.currentBackStackEntry?.arguments?.putLong(
            NOTE_ID,
            noteId
        )
        navController.navigate("$NOTE_DETAILS/${NOTE_ID}")
    }
    val addScreen: (Note) -> Unit = { note: Note ->
        navController.currentBackStackEntry?.arguments?.putLong(
            NOTE_ID,
            note.id
        )
        navController.navigate("$NOTE_KEY/${NOTE_ID}")
    }

    val mainScreen: () -> Unit = {
        navController.navigate(NOTES)
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}