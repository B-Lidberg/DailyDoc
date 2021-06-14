package com.lid.dailydoc

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.lid.dailydoc.navigation.Navigation
import com.lid.dailydoc.presentation.ui.theme.DailyDocTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @ObsoleteCoroutinesApi
    @ExperimentalAnimationApi
    @ExperimentalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyDocTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}
