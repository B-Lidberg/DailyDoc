package com.lid.dailydoc.presentation.screens.drawer_screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren

@Composable
fun UserScreen(
    vm: UserViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
) {

    val username by vm.currentUsername.observeAsState()

    val cUsername by vm.currentUsername.observeAsState()
    val cPassword by vm.currentPassword.observeAsState()
    val cDrawerState by vm.currentUiDrawerState.observeAsState()


    Scaffold(scaffoldState = scaffoldState) {

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically,
            ) {
                UserSettings(vm)
                Spacer(modifier = Modifier.padding(end = 12.dp))
                Text(text = "Account", fontSize = 24.sp, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.padding(24.dp))
            Text("User: $username")
            Text(text = "username: $cUsername")
            Text(text = "password: $cPassword")
            Text(text = "screen: $cDrawerState")
        }
    }
}

@Composable
fun UserSettings(vm: UserViewModel) {
    var showSettings by remember { mutableStateOf(false) }

    IconButton(onClick = { showSettings = true }) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Settings"
        )
    }
    DropdownMenu(
        expanded = showSettings,
        onDismissRequest = { showSettings = false }
    ) {
        SignOutButton {
            showSettings = false
                vm.signOut()
                vm.navigateToLoginScreen()
            }
        }
    }

@Composable
fun SignOutButton(logoutUser: () -> Unit) {
    Button(
        onClick = {
            logoutUser()
        }
    ) {
        Text("Sign Out")
    }
}