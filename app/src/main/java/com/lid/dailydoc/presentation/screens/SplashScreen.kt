package com.lid.dailydoc.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lid.dailydoc.viewmodels.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    vm: LoginViewModel,
    toMain: () -> Unit,
    toLogin: () -> Unit,
    ) {
    val signIn by vm.signedIn.observeAsState(vm.signedIn.value!!)
    val user by vm.user.observeAsState("")
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Welcome!", style = MaterialTheme.typography.h3)
        Text(user, style = MaterialTheme.typography.h5)
        LaunchedEffect(signIn) {
            delay(1500)
            if (signIn) {
                toMain()
            } else {
                toLogin()
            }
        }
        LaunchedEffect(!signIn) {
            delay(1500)
            if (signIn) {
                toMain()
            } else {
                toLogin()
            }
        }
    }
}