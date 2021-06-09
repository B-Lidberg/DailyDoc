package com.lid.dailydoc.presentation.screens.drawer_screens

import android.widget.ProgressBar
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.navigation.UiDrawerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    signedIn: Boolean,
    uiState: MutableTransitionState<UiDrawerState>,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    Scaffold(
        scaffoldState = scaffoldState
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProgressBar()

        }
        LaunchedEffect(signedIn) {
            delay(1000)
            if (signedIn) {
                uiState.targetState = UiDrawerState.LOGGED_IN
            } else {
                uiState.targetState = UiDrawerState.LOGGED_OUT
            }
        }
    }
}

@Composable
fun ProgressBar() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Loading...", style = MaterialTheme.typography.h4)
        LinearProgressIndicator(
            Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        Text("(1 second delay set to feature progress bar)")
    }
}