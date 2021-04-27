package com.lid.dailydoc.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.lid.dailydoc.presentation.screens.LoginScreen
import com.lid.dailydoc.presentation.screens.UserScreen
import com.lid.dailydoc.viewmodels.UserViewModel


enum class UiDrawerState {
    LOADING,
    LOGGED_IN,
    LOGGED_OUT
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun DrawerNavigation(
    vm: UserViewModel,
) {
    val uiState = remember { MutableTransitionState(UiDrawerState.LOADING) }

    Crossfade(
        targetState = uiState.currentState,
        animationSpec = tween(500)
    ) { state ->
        updateTransition(uiState, label = "drawer_screen")

        when (state) {
            UiDrawerState.LOADING -> LoginScreen(vm, uiState)

            UiDrawerState.LOGGED_IN -> UserScreen(vm, uiState)

            UiDrawerState.LOGGED_OUT -> LoginScreen(vm, uiState)

        }
    }
}