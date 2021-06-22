package com.lid.dailydoc.utils

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.MutableState
import com.lid.dailydoc.other.Resource
import com.lid.dailydoc.other.Status
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber


fun checkLogin(
    userVm: UserViewModel,
    result: Resource<String>?,
    loadingVisibility: MutableState<Boolean>,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    trimmedUsername: String,
    trimmedPassword: String
) {
    result?.let {
        val signedIn = userVm.signedIn.value
        when (result.status) {
            Status.SUCCESS -> {
                loadingVisibility.value = false
                scope.coroutineContext.cancelChildren()
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        result.data ?: "Successfully logged in"
                    )
                }
                userVm.authenticateApi(trimmedUsername, trimmedPassword)
                Timber.d("CALLED SUCCESS")
                Timber.d("BEFORE SET DATA: $signedIn")
                userVm.setUserData(trimmedUsername, trimmedPassword)
                userVm.navigateToUserScreen()
                Timber.d("AFTER SET DATA: $signedIn")
            }
            Status.ERROR -> {
                loadingVisibility.value = false
                scope.coroutineContext.cancelChildren()
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        result.message ?: "An unknown error occurred"
                    )
                }
                Timber.d("ERROR: $signedIn")

            }
            Status.LOADING -> {
                scope.launch {
                    delay(250)
                    loadingVisibility.value = true
                }
            }
        }
    }
}