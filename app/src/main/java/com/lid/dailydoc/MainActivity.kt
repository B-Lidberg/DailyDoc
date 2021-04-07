package com.lid.dailydoc

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import com.lid.dailydoc.presentation.ui.theme.DailyDocTheme
import com.lid.dailydoc.presentation.viewmodels.*


class MainActivity : ComponentActivity() {

    private val noteListVm: NoteViewModel by viewModels {
        NoteViewModeFactory((application as NotesApplication).repository)
    }

    private val noteAddVm: NoteAddViewModel by viewModels {
        NoteAddViewModeFactory((application as NotesApplication).repository)
    }

    @ExperimentalAnimationApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyDocTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(noteListVm, noteAddVm)
                }
            }
        }
    }
}
