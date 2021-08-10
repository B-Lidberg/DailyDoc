package com.lid.dailydoc.presentation.screens.drawer_screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.lid.dailydoc.data.extras.appName
import com.lid.dailydoc.other.Status
import com.lid.dailydoc.presentation.components.ProgressBar
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    vm: UserViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
) {

    val visibility = remember { mutableStateOf(false) }

    val username = rememberSaveable { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmedPassword = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }

    val currentLifeCycle = LocalLifecycleOwner.current

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { vm.navigateToLoginScreen() }
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
                            Icons.Default.Visibility,
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
                            Icons.Default.Visibility,
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
                    val trimmedUsername = username.value.trim()
                    val trimmedPassword = password.value.trim()
                    val trimmedConfirmedPassword = confirmedPassword.value.trim()
                    vm.registerUser(
                        trimmedUsername,
                        trimmedPassword,
                        trimmedConfirmedPassword
                    )
                    vm.registerStatus.observe(currentLifeCycle, { result ->
                        result?.let {
                            when (result.status) {
                                Status.SUCCESS -> {
                                    scope.coroutineContext.cancelChildren()
                                    visibility.value = false
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            result.data ?: "Successfully registered an account"
                                        )
                                    }
                                    vm.authenticateApi(trimmedUsername, trimmedPassword)
                                    vm.setUserData(trimmedUsername, trimmedPassword)
                                    vm.navigateToUserScreen()
                                }
                                Status.ERROR -> {
                                    scope.coroutineContext.cancelChildren()
                                    visibility.value = false
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            result.message ?: "An unknown error occurred"
                                        )
                                    }

                                }
                                Status.LOADING -> {
                                    visibility.value = true
                                }
                            }
                        }
                    })
                },
                enabled =
                username.value.trim().isNotEmpty() &&
                        password.value.trim().isNotEmpty() &&
                        confirmedPassword.value.trim().isNotEmpty() &&
                        password.value.trim() == confirmedPassword.value.trim(),
                modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
            ) {
                Text(text = "Register")

            }
            Spacer(modifier = Modifier.padding(top = 16.dp))


            Text(
                text = "Remember your username / password as there is no account recovery. " +
                        "Keep in mind when sharing notes " +
                        "others will see your username.",
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )
            ProgressBar(visibility = visibility.value)
        }
    }
}


