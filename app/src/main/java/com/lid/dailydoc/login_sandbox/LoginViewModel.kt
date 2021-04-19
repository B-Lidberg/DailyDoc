package com.lid.dailydoc.login_sandbox

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val app: Application,
    private val authRepository: AuthRepository
) : AndroidViewModel(app) {

    //note: You have to add SHA1 for google login to work.

    //to get SHA1 for debug mode, run this: keytool -list -v -keystore <debug keystore path> -alias androiddebugkey -storepass android -keystore android
    //debug keystore path example: C:\Users\sheri\.android\debug.keystore (replace "\Users\sheri\" with your username on your computer.

    //to get SHA1 for release mode, run this: keytool -list -v -alias keytstore -keystore <keystore path> -alias <alias name>
    //keystore path example: F:\Ameen\upload-keystore.jks
    //keystore alias name example: upload

    init {
        Firebase.auth.addAuthStateListener {
            _signedIn.value = it.currentUser != null
            _user.value = it.currentUser?.email ?: "Navigating to Login Screen..."
        }
    }

    private val _user = MutableLiveData("")
    val user: LiveData<String> = _user

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _signedIn = MutableLiveData(false)
    val signedIn: LiveData<Boolean> = _signedIn

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun removeError() {
        _error.value = null
    }

    fun signOut() = Firebase.auth.signOut()

    @RequiresApi(Build.VERSION_CODES.M)
    fun loginWithGoogle(idToken: String) = viewModelScope.launch {
        try {
            app.connectedOrThrow()
            _loading.postValue(true)
            authRepository.loginWithGoogle(idToken)
            _signedIn.postValue(true)
        } catch (e: Exception) {
            _error.postValue(e.localizedMessage)
        } finally {
            _loading.postValue(false)
        }
    }

}

class LoginViewModelFactory(
    private val app: Application,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(app, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}