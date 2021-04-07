package com.lid.dailydoc.playground_package


//import android.app.Activity
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.OutlinedTextField
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import com.lid.dailydoc.presentation.viewmodels.LoginViewModel
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.firebase.auth.UserInfo
//

//@Composable
//fun LoginScreen(
//    vm: LoginViewModel,
//    toMain: (UserInfo) -> Unit
//    ) {
//    val userNameErrorState = vm.userNameErrorLiveData.observeAsState()
//    val passwordErrorState = vm.passwordErrorLiveData.observeAsState()
//    val username = remember { mutableStateOf(TextFieldValue("")) }
//    val password = remember { mutableStateOf(TextFieldValue("")) }
//
//    Column {
//        Text("Sign In")
//        OutlinedTextField(
//            value = username.value,
//            onValueChange = { username.value = it },
//            placeholder = { Text(text = "Username") },
//            label = { Text(text = "Username") },
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth(),
//            isError = userNameErrorState.value != null,
//        )
//        Text(
//            text = userNameErrorState.value ?: "",
//            textAlign = TextAlign.Start,
//            modifier = Modifier.height(userNameErrorState.value?.let { 20.dp } ?: 0.dp),
//            color = MaterialTheme.colors.error
//        )
//        Spacer(Modifier.height(30.dp))
//
//        OutlinedTextField(
//            value = password.value,
//            onValueChange = {password.value = it },
//            placeholder = { Text(text = "Password") },
//            label = { Text(text = "Password") },
//
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth(),
//            isError = passwordErrorState.value != null,
//            visualTransformation = PasswordVisualTransformation()
//        )
//        Text(
//            text = passwordErrorState.value ?: "",
//            textAlign = TextAlign.Start,
//            modifier = Modifier.height(passwordErrorState.value?.let { 20.dp } ?: 0.dp),
//            color = MaterialTheme.colors.error
//        )
//        Spacer(Modifier.height(16.dp))
//
//        Button(onClick = {
//            vm.login(username.value.text, password.value.text)
//            val user = UserInfo(true, username.value.text)
//            toMain(user)
//        }) {
//            Text("Login")
//        }
//        Button(onClick = {  }) {
//            Text("Use Google")
//        }
//    }
//}
//
//private fun startSignInFlow() {
//    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken("844436319960-tupni9c3p3jnbpagqcvrrmpno8t3es0v.apps.googleusercontent.com")
//        .requestEmail()
//        .build()
//    val googleSignInClient = GoogleSignIn.getClient(Activity(), gso)
//    val signInIntent = googleSignInClient.signInIntent
//}