@file:Suppress("NAME_SHADOWING")

package com.lid.dailydoc

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.lid.dailydoc.MainDestinations.LOGIN
import com.lid.dailydoc.MainDestinations.NOTES
import com.lid.dailydoc.MainDestinations.NOTE_DETAILS
import com.lid.dailydoc.MainDestinations.NOTE_ID
import com.lid.dailydoc.MainDestinations.NOTE_KEY
import com.lid.dailydoc.MainDestinations.SPLASH
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.presentation.screens.*
import com.lid.dailydoc.viewmodels.UserViewModel
import com.lid.dailydoc.viewmodels.NoteAddViewModel
import com.lid.dailydoc.viewmodels.NoteDetailViewModel
import com.lid.dailydoc.viewmodels.NoteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

object MainDestinations {
    const val NOTES = "notes"
    const val NOTE_DETAILS = "note_details"
    const val NOTE_ID = "noteId"
    const val NOTE_KEY = "note"
    const val LOGIN = "login"
    const val SPLASH = "loading"
}

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    startDestination: String = SPLASH,
) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    val addNoteVm = hiltNavGraphViewModel<NoteAddViewModel>()

    NavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = NOTES,
        ) {

            val noteListVm = hiltNavGraphViewModel<NoteViewModel>()

            addNoteVm.setDate()
            addNoteVm.cacheNote()
            val note = addNoteVm.cachedNote
            val loginVm = hiltNavGraphViewModel<UserViewModel>(backStackEntry = it)

            NoteListScreen(
                noteListVm, loginVm,
                actions.detailScreen, actions.addScreen,
                note
            )
        }
        val noteId = navController.previousBackStackEntry?.arguments?.getLong(NOTE_ID)
        val note = noteId?.let { addNoteVm.getNoteById(it) }
        if (noteId != null) {
            composable("$NOTE_KEY/${NOTE_ID}") {
                NoteAddScreen(addNoteVm, actions.upPress, note!!) }
        } else {
            composable("$NOTE_KEY/${NOTE_ID}") {
                NoteAddScreen(addNoteVm, actions.upPress, addNoteVm.cachedNote) }
        }

        composable("$NOTE_DETAILS/${NOTE_ID}") {
            val detailVm = hiltNavGraphViewModel<NoteDetailViewModel>(backStackEntry = it)

            val noteId = navController.previousBackStackEntry?.arguments?.getLong(NOTE_ID)
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
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
    val mainScreen: () -> Unit = {
        navController.navigate(NOTES) {
            popUpTo(0) { inclusive = true }
        }
    }

    val loginScreen: () -> Unit = {
        navController.navigate(LOGIN) {
            popUpTo(0) { inclusive = true }
        }
    }
}