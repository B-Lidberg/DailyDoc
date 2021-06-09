package com.lid.dailydoc.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.lid.dailydoc.presentation.screens.drawer_screens.*
import com.lid.dailydoc.viewmodels.UserViewModel


enum class UiDrawerState {
    LOADING,
    LOGGED_IN,
    LOGGED_OUT,
    REGISTER,
    INFO
}

@Composable
fun DrawerNavigation(
    vm: UserViewModel,
) {
    val uiState = remember { MutableTransitionState(UiDrawerState.LOADING) }
    val signedIn by vm.signedIn.observeAsState(vm.isLoggedIn())

    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Crossfade(
        targetState = uiState.currentState,
        animationSpec = tween(600)
    ) { state ->
        updateTransition(uiState, label = "drawer_screen")
        when (state) {
            UiDrawerState.LOADING -> LoadingScreen(signedIn, uiState, scaffoldState, scope)

            UiDrawerState.LOGGED_IN -> UserScreen(vm, uiState, scaffoldState, scope)

            UiDrawerState.LOGGED_OUT -> LoginScreen(vm, uiState, scaffoldState, scope)

            UiDrawerState.REGISTER -> RegisterScreen(vm, uiState, scaffoldState, scope)

            UiDrawerState.INFO -> { InfoScreen(uiState, scaffoldState, scope) }

        }
    }
}