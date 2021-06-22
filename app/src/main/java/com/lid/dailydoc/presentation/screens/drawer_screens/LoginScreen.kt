package com.lid.dailydoc.presentation.screens.drawer_screens


import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.lid.dailydoc.R
import com.lid.dailydoc.data.extras.appName
import com.lid.dailydoc.presentation.components.GoogleSignInOption
import com.lid.dailydoc.presentation.components.ProgressBar
import com.lid.dailydoc.utils.checkLogin
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

@Composable
fun LoginScreen(
    vm: UserViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                vm.loginWithGoogle(credential)
                vm.navigateToUserScreen()
            } catch (e: ApiException) {
                Timber.tag("TAG").w(e, "Google sign in failed")
            }
        }

    val context = LocalContext.current
    val token = stringResource(R.string.default_web_client_id)


    val username = rememberSaveable { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val loadingVisibility = remember { mutableStateOf(false) }

    val currentLifeCycle = LocalLifecycleOwner.current

    Scaffold(
        scaffoldState = scaffoldState,
    ) {

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
                visualTransformation = if (passwordVisibility.value) None
                else PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                )
            )
            Button(
                onClick = {
                    val trimmedUsername = username.value.trim()
                    val trimmedPassword = password.value.trim()
                    vm.loginWithUsername(trimmedUsername, trimmedPassword)
                    vm.loginStatus.observe(currentLifeCycle, { result ->
                        checkLogin(
                            userVm = vm,
                            result = result,
                            loadingVisibility = loadingVisibility,
                            scope = scope,
                            scaffoldState = scaffoldState,
                            trimmedUsername = trimmedUsername,
                            trimmedPassword = trimmedPassword
                        )
                    })
                },
                enabled = username.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
            ) {
                Text(text = "Sign In")

            }
            TextButton(
                onClick = { vm.navigateToRegisterScreen() },
            ) {
                Text(
                    text = "Click here to register an account!"
                )

            }

            GoogleSignInOption(enabled = true) {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            }
            ProgressBar(visibility = loadingVisibility.value)
        }
    }
}



