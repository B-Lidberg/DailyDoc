package com.lid.dailydoc.presentation.screens.drawer_screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.UserData.UiDrawerState
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    vm: UserViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    LaunchedEffect(true) {
        delay(1000)
        if (vm.signedIn.value == true) {
            vm.navigateToUserScreen()
        } else {
            vm.navigateToLoginScreen()
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProgressBar()

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