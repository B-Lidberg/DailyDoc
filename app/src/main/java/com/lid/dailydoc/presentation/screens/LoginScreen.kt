package com.lid.dailydoc.presentation.screens

/** Not Implemented Yet. Still testing
 *  May try to stick to Single Activity or decide on building a Login Activity
 *  Firebase Auth / Google Sign in?
 */

/*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.UserInfo


@Composable
fun LoginScreen(
    toMain: (UserInfo) -> Unit,
    ) {
//    val userNameErrorState = vm.userNameErrorLiveData.observeAsState()
//    val passwordErrorState = vm.passwordErrorLiveData.observeAsState()
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val password = remember { mutableStateOf(TextFieldValue("")) }


    Column {
        Text("Sign In")
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            placeholder = { Text(text = "Username") },
            label = { Text(text = "Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
//            isError = userNameErrorState.value != null,
        )
//        Text(
//            text = userNameErrorState.value ?: "",
//            textAlign = TextAlign.Start,
//            modifier = Modifier.height(userNameErrorState.value?.let { 20.dp } ?: 0.dp),
//            color = MaterialTheme.colors.error
//        )
        Spacer(Modifier.height(30.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = {password.value = it },
            placeholder = { Text(text = "Password") },
            label = { Text(text = "Password") },

            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
//            isError = passwordErrorState.value != null,
            visualTransformation = PasswordVisualTransformation()
        )
//        Text(
//            text = passwordErrorState.value ?: "",
//            textAlign = TextAlign.Start,
//            modifier = Modifier.height(passwordErrorState.value?.let { 20.dp } ?: 0.dp),
//            color = MaterialTheme.colors.error
//        )
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
//            vm.login(username.value.text, password.value.text)
//            val user = UserInfo(true, username.value.text)
//            toMain(user)
        }) {
            Text("Login")
        }
        Button(onClick = {  }) {
            Text("Use Google")
        }
    }
}
*/
