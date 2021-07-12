package com.lid.dailydoc.navigation

import com.lid.dailydoc.other.Constants.NOTES
import com.lid.dailydoc.other.Constants.NOTE_DETAILS
import com.lid.dailydoc.other.Constants.NOTE_ID
import com.lid.dailydoc.other.Constants.NOTE_KEY
import com.lid.dailydoc.other.Constants.SPLASH

sealed class Screen(var route: String) {
    object NoteList : Screen(route = NOTES)
    object AddNote : Screen(route = "$NOTE_KEY/${NOTE_ID}")
    object NoteDetail : Screen(route = "$NOTE_DETAILS/${NOTE_ID}")
    object Splash : Screen(route = SPLASH)
}