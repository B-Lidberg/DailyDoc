package com.lid.dailydoc.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.lid.dailydoc.UserData.UiDrawerState
import com.lid.dailydoc.presentation.screens.drawer_screens.*
import com.lid.dailydoc.viewmodels.UserViewModel

@Composable
fun DrawerNavigation(
    vm: UserViewModel,
) {

    val uiDrawerState by vm.currentUiDrawerState.observeAsState(vm.currentUiDrawerState.value ?: UiDrawerState.LOADING)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Crossfade(
        targetState = uiDrawerState,
        animationSpec = tween(600)
    ) { state ->
        updateTransition(uiDrawerState, label = "drawer_screen")
        when (state) {
            UiDrawerState.LOADING -> LoadingScreen(vm, scaffoldState, scope)

            UiDrawerState.LOGGED_IN -> UserScreen(vm, scaffoldState, scope)

            UiDrawerState.LOGGED_OUT -> LoginScreen(vm, scaffoldState, scope)

            UiDrawerState.REGISTER -> RegisterScreen(vm, scaffoldState, scope)

            UiDrawerState.INFO -> { InfoScreen(vm, scaffoldState, scope) }


        }
    }
}