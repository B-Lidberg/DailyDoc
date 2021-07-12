@file:Suppress("NAME_SHADOWING")

package com.lid.dailydoc.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.other.Constants.NOTE_ID
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

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun Navigation(
    startDestination: String = Screen.Splash.route,
) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Screen.NoteList.route) {
//            val noteListVm = hiltViewModel<NoteViewModel>()
//            val userVm = hiltViewModel<UserViewModel>()

//            val note by noteListVm.currentNote.observeAsState(noteListVm.getCurrentNote())

            NoteListScreen(
                toDetails = actions.detailScreen, toAdd = actions.addScreen,
            )
        }

        composable(Screen.AddNote.route) {
            val addNoteVm = hiltViewModel<NoteAddViewModel>()

            val noteId = navController.previousBackStackEntry?.arguments?.getString(NOTE_ID)
            val note = noteId?.let { addNoteVm.getNoteById(noteId) } ?: Note(getCurrentDateAsLong())
            NoteAddScreen(addNoteVm, actions.upPress, note)
        }

        composable(Screen.NoteDetail.route) {
            val detailVm = hiltViewModel<NoteDetailViewModel>(backStackEntry = it)

            val noteId = navController.previousBackStackEntry?.arguments?.getString(NOTE_ID)
            if (noteId != null) NoteDetailScreen { detailVm.getNote(noteId) }
        }

        composable(Screen.Splash.route) {
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
        navController.navigate(Screen.NoteDetail.route)
    }
    val addScreen: (Note) -> Unit = { note: Note ->
        navController.currentBackStackEntry?.arguments?.putString(
            NOTE_ID,
            note.noteId
        )
        navController.navigate(Screen.AddNote.route)
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
    val mainScreen: () -> Unit = {
        navController.navigate(Screen.NoteList.route) {
            popUpTo(0) { inclusive = true }
        }
    }
}