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

@Composable
fun UserScreen(
    vm: UserViewModel,
    scaffoldState: ScaffoldState,
    syncNotes: () -> Unit,
) {

    val username by vm.currentUsername.observeAsState()
    val password by vm.currentPassword.observeAsState()

    val cDrawerState by vm.currentUiDrawerState.observeAsState()

    LaunchedEffect(username) {
        vm.authenticateApi(username ?: "", password ?: "")
        syncNotes()
    }


    Scaffold(scaffoldState = scaffoldState) {

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically,
            ) {
                UserSettings { vm.signOut() }
                Spacer(modifier = Modifier.padding(end = 12.dp))
                Text(text = "Account", fontSize = 24.sp, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.padding(24.dp))
            Text("User: $username")
            Text(text = "password: $password")
            Text(text = "screen: $cDrawerState")
        }
    }
}

@Composable
fun UserSettings(signOut: () -> Unit) {
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
            signOut()
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