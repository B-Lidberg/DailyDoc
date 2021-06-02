package com.lid.dailydoc.presentation.screens.drawer_screens

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.lid.dailydoc.data.extras.appName
import com.lid.dailydoc.navigation.UiDrawerState
import com.lid.dailydoc.other.Status
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun RegisterScreen(
    vm: UserViewModel,
    uiState: MutableTransitionState<UiDrawerState>
) {

    val signIn by vm.signedIn.observeAsState(vm.signedIn.value!!)

    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val confirmedPassword = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(signIn) {
        if (signIn) uiState.targetState = UiDrawerState.LOADING
    }

//    LaunchedEffect() {
    vm.loginStatus.observeForever(Observer { result ->
            result?.let {
                scope.launch {
                when (result.status) {
                    Status.SUCCESS -> {
                        snackbarHostState.showSnackbar(result.data ?: "Successfully logged in")
                        vm.authenticateApi(username.value, password.value)
                        Timber.d("CALLED")
                        uiState.targetState = UiDrawerState.LOGGED_IN
//                            redirectLogin()
                    }
                    Status.ERROR -> {
                        snackbarHostState.showSnackbar(
                            result.message ?: "An unknown error occurred"
                        )
                    }
                    Status.LOADING -> {


                    }
                }
            }
        }
    })
//    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { uiState.targetState = UiDrawerState.LOGGED_OUT }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Back to LoginScreen"
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = appName,
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp)
            )
            Text(
                text = "Register"
            )
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text(text = "Username") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                )
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text(text = "Password") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "Change password visibility",
                            tint = if (passwordVisibility.value) MaterialTheme.colors.primary
                            else Color.LightGray
                        )
                    }
                },
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                else PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor =
                    if (password.value.trim() == confirmedPassword.value.trim() &&
                        password.value.trim().isNotEmpty() && confirmedPassword.value.trim()
                            .isNotEmpty()
                    ) {
                        Color.Green
                    } else {
                        MaterialTheme.colors.primary
                    },
                    unfocusedBorderColor =
                    if (password.value.trim() == confirmedPassword.value.trim() &&
                        password.value.trim().isNotEmpty() && confirmedPassword.value.trim()
                            .isNotEmpty()
                    ) {
                        Color.Green
                    } else {
                        MaterialTheme.colors.primary
                    },
                    focusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                )
            )
            OutlinedTextField(
                value = confirmedPassword.value,
                onValueChange = { confirmedPassword.value = it },
                label = { Text(text = "Confirm Password") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "Change password visibility",
                            tint = if (passwordVisibility.value) MaterialTheme.colors.primary
                            else Color.LightGray
                        )
                    }
                },
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                else PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor =
                    if (password.value.trim() == confirmedPassword.value.trim() &&
                        password.value.trim().isNotEmpty() && confirmedPassword.value.trim()
                            .isNotEmpty()
                    ) {
                        Color.Green
                    } else if (confirmedPassword.value.trim().isEmpty()) {
                        MaterialTheme.colors.primary
                    } else {
                        Color.Red
                    },
                    unfocusedBorderColor =
                    if (password.value.trim() == confirmedPassword.value.trim() &&
                        password.value.trim().isNotEmpty() && confirmedPassword.value.trim()
                            .isNotEmpty()
                    ) {
                        Color.Green
                    } else if (password.value.trim()
                            .isEmpty() || confirmedPassword.value.isEmpty()
                    ) {
                        MaterialTheme.colors.primary
                    } else {
                        Color.Red
                    },
                    focusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                ),
            )
            Button(
                onClick = {
                    vm.registerUser(
                        username.value.trim(),
                        password.value.trim(),
                        confirmedPassword = confirmedPassword.value.trim()
                    )
                    vm.registerStatus.observeForever { result ->
                        scope.launch {
                            result?.let {
                                when (result.status) {
                                    Status.SUCCESS -> {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = result.data
                                                ?: "Successfully registered an account",
                                        )
                                        vm.authenticateApi(username.value, password.value)
                                        Timber.d("CALLED")
                                        uiState.targetState = UiDrawerState.LOGGED_IN
                                    }
                                    Status.ERROR -> {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = result.message
                                                ?: "An unknown error occurred",
                                        )
                                    }
                                    Status.LOADING -> {

                                    }

                                }
                            }
                        }
                    }
                },
                enabled =
                username.value.trim().isNotEmpty() &&
                        password.value.trim().isNotEmpty() &&
                        confirmedPassword.value.trim().isNotEmpty() &&
                        password.value.trim() == confirmedPassword.value.trim()
            ) {
                Text(text = "Register")

            }
            Spacer(modifier = Modifier.padding(top = 16.dp))


            Text(
                text = "Remember your username / password as there is no account recovery. " +
                        "Keep in mind when sharing notes " +
                        "others will see your username.",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}



