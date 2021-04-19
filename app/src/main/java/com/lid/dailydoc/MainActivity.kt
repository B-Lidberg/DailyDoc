package com.lid.dailydoc

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.lid.dailydoc.data.repository.AuthRepository
import com.lid.dailydoc.viewmodels.LoginViewModel
import com.lid.dailydoc.viewmodels.LoginViewModelFactory
import com.lid.dailydoc.presentation.ui.theme.DailyDocTheme
import com.lid.dailydoc.viewmodels.NoteViewModel
import com.lid.dailydoc.viewmodels.NoteViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi


class MainActivity : ComponentActivity() {

    private val noteListVm: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    private val loginVm: LoginViewModel by viewModels {
        LoginViewModelFactory((application as NotesApplication), AuthRepository.getInstance())
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
                        Navigation(noteListVm, loginVm)
                }
            }
        }
    }
}
