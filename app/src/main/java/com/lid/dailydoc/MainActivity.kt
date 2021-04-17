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
import com.lid.dailydoc.viewmodels.*
import kotlinx.coroutines.*


class MainActivity : ComponentActivity() {

    private val noteListVm: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalAnimationApi
    @ExperimentalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyDocTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(noteListVm)
                }
            }
        }
    }
}
