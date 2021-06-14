package com.lid.dailydoc.presentation.screens.drawer_screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lid.dailydoc.presentation.components.ProgressBar
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    vm: UserViewModel,
    scaffoldState: ScaffoldState,
) {

    val signedIn by vm.signedIn.observeAsState()

    LaunchedEffect(signedIn) {
        vm.setLoginBoolean()
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
            ProgressBar(true)

        }

    }
}
