package com.lid.dailydoc.presentation.screens.drawer_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@Composable
fun UserScreen(
    currentUsername: LiveData<String>,
    scaffoldState: ScaffoldState,
    signOut: () -> Unit,
    syncNotes: () -> Unit,
) {

    val username by currentUsername.observeAsState()

    LaunchedEffect(username) {
        syncNotes()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            UserSettings { signOut() }
        }
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            GradientProfileBox()
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.2f),
                    verticalArrangement = Arrangement.Top
                ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colors.onSurface.copy(alpha = .5f),
                            modifier = Modifier
                                .padding(8.dp)
                                .size(75.dp),
                            content = { }
                        )
                        Text(text = username ?: "", style = MaterialTheme.typography.h5)


                }
                LazyColumn {
                    item { OptionCard(text = "About", icon = Icons.Outlined.Info) }
                    item { OptionCard(text = "Friends", icon = Icons.Outlined.AddCircle) }
                }
            }
        }
    }
}

@Composable
fun UserSettings(signOut: () -> Unit) {
    var showSettings by remember { mutableStateOf(false) }

    val horizontalGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colors.secondaryVariant.copy(.6f),
            MaterialTheme.colors.secondary.copy(.95f),
            MaterialTheme.colors.secondary.copy(.5f),
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = horizontalGradientBrush),

    ) {
        IconButton(onClick = { showSettings = true }) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Settings"
            )
        }
        DropdownMenu(
            expanded = showSettings,
            onDismissRequest = { showSettings = false },
            modifier = Modifier.background(brush = horizontalGradientBrush)
        ) {
            SignOutButton {
                showSettings = false
                signOut()
            }
        }
    }
}

@Composable
fun SignOutButton(logoutUser: () -> Unit) {
    TextButton(
        onClick = {
            logoutUser()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    ) {
        Text("Sign Out")
    }
}

@Composable
fun OptionCard(text: String, icon: ImageVector, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = "Option: $text",
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .size(40.dp)
            )
            Text(text, style = MaterialTheme.typography.h6, color = Color.Unspecified.copy(.75f))
        }
    }
}

@Composable
fun GradientProfileBox() {

    val horizontalGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colors.secondaryVariant.copy(.6f),
            MaterialTheme.colors.secondary.copy(.95f),
            MaterialTheme.colors.secondary.copy(.5f),
        )
    )
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.2f)
                .background(brush = horizontalGradientBrush)
        ) {
        }
    }
}

@Preview
@Composable
fun PreviewComposableUserScreen() {

    val scaffoldState = rememberScaffoldState()
    val username: MutableLiveData<String> by lazy {
        MutableLiveData<String>("Lidberg")
    }
    UserScreen(
        currentUsername = username,
        scaffoldState = scaffoldState,
        signOut = { /*TODO*/ },
        syncNotes = { /*TODO*/ }
    )
}