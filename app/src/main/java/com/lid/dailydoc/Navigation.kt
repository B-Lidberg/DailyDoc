@file:Suppress("NAME_SHADOWING")

package com.lid.dailydoc

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.lid.dailydoc.authorization.LoginScreen
import com.lid.dailydoc.viewmodels.LoginViewModel
import com.lid.dailydoc.authorization.SplashScreen
import com.lid.dailydoc.presentation.screens.NoteAddScreen
import com.lid.dailydoc.presentation.screens.NoteDetailScreen
import com.lid.dailydoc.presentation.screens.NoteListScreen
import com.lid.dailydoc.viewmodels.*
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
    noteListVm: NoteViewModel,
    loginVm: LoginViewModel,
    startDestination: String = SPLASH,
) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    val addNoteVm: NoteAddViewModel = viewModel(
        factory = NoteAddViewModelFactory(NotesApplication().repository))

    val signOut = { loginVm.signOut() }

    NavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = NOTES,
        ) {
            addNoteVm.setDate()
            addNoteVm.cacheNote()
            val note = addNoteVm.cachedNote
            NoteListScreen(noteListVm, actions.detailScreen, actions.addScreen, note, actions.loginScreen, signOut)
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

        composable(
            route = LOGIN,
        ) {
            LoginScreen(loginVm, actions.mainScreen, actions.splashScreen)
        }

        composable(
            route = SPLASH,
        ) {
            SplashScreen(loginVm, actions.mainScreen, actions.loginScreen)
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
    val splashScreen: () -> Unit = {
        navController.navigate(SPLASH) {
            popUpTo(0) { inclusive = true }
        }
    }
}