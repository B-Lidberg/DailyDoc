@file:Suppress("NAME_SHADOWING")

package com.lid.dailydoc.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.navigation.MainDestinations.NOTES
import com.lid.dailydoc.navigation.MainDestinations.NOTE_DETAILS
import com.lid.dailydoc.navigation.MainDestinations.NOTE_ID
import com.lid.dailydoc.navigation.MainDestinations.NOTE_KEY
import com.lid.dailydoc.navigation.MainDestinations.SPLASH
import com.lid.dailydoc.presentation.screens.NoteAddScreen
import com.lid.dailydoc.presentation.screens.NoteDetailScreen
import com.lid.dailydoc.presentation.screens.NoteListScreen
import com.lid.dailydoc.presentation.screens.SplashScreen
import com.lid.dailydoc.utils.getCurrentDateAsLong
import com.lid.dailydoc.viewmodels.NoteAddViewModel
import com.lid.dailydoc.viewmodels.NoteDetailViewModel
import com.lid.dailydoc.viewmodels.NoteViewModel
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

object MainDestinations {
    const val NOTES = "notes"
    const val NOTE_DETAILS = "note_details"
    const val NOTE_ID = "noteId"
    const val NOTE_KEY = "note"
    const val SPLASH = "loading"
}

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun Navigation(
    startDestination: String = SPLASH,
) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }


    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = NOTES) {
            val userVm = hiltViewModel<UserViewModel>()
            val noteListVm = hiltViewModel<NoteViewModel>()
            val note by noteListVm.currentNote.observeAsState(noteListVm.getCurrentNote())

            NoteListScreen(
                userVm, noteListVm,
                actions.detailScreen, actions.addScreen,
                note
            )
        }
        composable("$NOTE_KEY/${NOTE_ID}") {
            val addNoteVm = hiltViewModel<NoteAddViewModel>()

            val noteId = navController.previousBackStackEntry?.arguments?.getString(NOTE_ID)
            val note = noteId?.let { addNoteVm.getNoteById(noteId) } ?: Note(getCurrentDateAsLong())
            NoteAddScreen(addNoteVm, actions.upPress, note)
        }

        composable("$NOTE_DETAILS/${NOTE_ID}") {
            val detailVm = hiltViewModel<NoteDetailViewModel>(backStackEntry = it)

            val noteId = navController.previousBackStackEntry?.arguments?.getString(NOTE_ID)
            if (noteId != null) NoteDetailScreen(detailVm, noteId)
        }

        composable(
            route = SPLASH,
        ) {
            SplashScreen(actions.mainScreen)
        }
    }
}

class MainActions(navController: NavController) {

    val detailScreen: (String) -> Unit = { noteId: String ->
        navController.currentBackStackEntry?.arguments?.putString(
            NOTE_ID,
            noteId
        )
        navController.navigate("$NOTE_DETAILS/${NOTE_ID}")
    }
    val addScreen: (Note) -> Unit = { note: Note ->
        navController.currentBackStackEntry?.arguments?.putString(
            NOTE_ID,
            note.noteId
        )
        navController.navigate("$NOTE_KEY/${NOTE_ID}")
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
    val mainScreen: () -> Unit = {
        navController.navigate(NOTES) {
            popUpTo(0) { inclusive = true }
        }
    }
}