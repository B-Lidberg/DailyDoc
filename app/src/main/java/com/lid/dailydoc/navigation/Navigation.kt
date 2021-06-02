@file:Suppress("NAME_SHADOWING")

package com.lid.dailydoc.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

    val addNoteVm = hiltViewModel<NoteAddViewModel>()

    NavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = NOTES,
        ) {

            val noteListVm = hiltViewModel<NoteViewModel>()

            addNoteVm.setDate()
            addNoteVm.cacheNote()
            val note = addNoteVm.cachedNote
            val loginVm = hiltViewModel<UserViewModel>(backStackEntry = it)

            NoteListScreen(
                noteListVm, loginVm,
                actions.detailScreen, actions.addScreen,
                note
            )
        }
        val noteId = navController.previousBackStackEntry?.arguments?.getString(NOTE_ID)
        val note = noteId?.let { addNoteVm.getNoteById(it) }
        if (noteId != null) {
            composable("$NOTE_KEY/${NOTE_ID}") {
                NoteAddScreen(addNoteVm, actions.upPress, note!!)
            }
        } else {
            composable("$NOTE_KEY/${NOTE_ID}") {
                NoteAddScreen(addNoteVm, actions.upPress, addNoteVm.cachedNote)
            }
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