package com.lid.dailydoc.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.lid.dailydoc.UserData.UiDrawerState
import com.lid.dailydoc.presentation.screens.drawer_screens.*
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun DrawerNavigation(
    vm: UserViewModel,
    syncNotes: () -> Unit,
) {

    val uiDrawerState by vm.currentUiDrawerState.observeAsState(vm.currentUiDrawerState.value ?: UiDrawerState.LOADING)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope { Dispatchers.IO }

    LaunchedEffect(vm.currentUsername) {
        syncNotes()
    }

    Crossfade(
        targetState = uiDrawerState,
        animationSpec = tween(600)
    ) { state ->
        updateTransition(uiDrawerState, label = "drawer_screen")
        when (state) {
            UiDrawerState.LOADING -> LoadingScreen(vm, scaffoldState)

            UiDrawerState.LOGGED_IN -> UserScreen(vm, scaffoldState, syncNotes)

            UiDrawerState.LOGGED_OUT -> LoginScreen(vm, scaffoldState, scope)

            UiDrawerState.REGISTER -> RegisterScreen(vm, scaffoldState, scope)

            UiDrawerState.INFO -> { InfoScreen(vm, scaffoldState, scope) }

            else -> LoadingScreen(vm, scaffoldState)


        }
    }
}