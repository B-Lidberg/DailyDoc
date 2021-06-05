package com.lid.dailydoc.viewmodels

import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.dailydoc.data.remote.BasicAuthInterceptor
import com.lid.dailydoc.data.repository.AuthRepository
import com.lid.dailydoc.data.repository.UserDataRepository
import com.lid.dailydoc.navigation.UiDrawerState
import com.lid.dailydoc.other.Resource
import com.lid.dailydoc.other.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _loginStatus = MutableLiveData<Resource<String>>()
    val loginStatus: LiveData<Resource<String>> = _loginStatus

    private val _registerStatus = MutableLiveData<Resource<String>>()
    val registerStatus: LiveData<Resource<String>> = _registerStatus

    private val _currentUsername = MutableLiveData<String>()
    val currentUsername: LiveData<String> = _currentUsername

    private val _currentPassword = MutableLiveData<String>()
    val currentPassword: LiveData<String> = _currentPassword

    internal var subContentState: ((UiDrawerState) -> Unit)? = null
    private val _user = MutableLiveData("")
    val user: LiveData<String> = _user

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _signedIn = MutableLiveData(false)
    val signedIn: LiveData<Boolean> = _signedIn

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error


    fun loginWithUsername(username: String, password: String) {
        _loginStatus.postValue(Resource.loading(null))
        if (username.isEmpty() || password.isEmpty()) {
            _loginStatus.postValue(Resource.error("Please fill out all fields", null))
            return
        }
        viewModelScope.launch {
            val result = authRepository.loginWithUsername(username, password)
            _loginStatus.postValue(result)
        }.invokeOnCompletion {
            if (userDataRepository.isLoggedIn()) {
                _currentUsername.value = username
                _currentPassword.value = password
                _user.value = _currentUsername.value
            }
        }
    }

    fun registerUser(username: String, password: String, confirmedPassword: String) {
        _registerStatus.postValue(Resource.loading(null))
        if (username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()) {
            _registerStatus.postValue(Resource.error("Please fill out all fields", null))
            return
        }
        if (password != confirmedPassword) {
            _registerStatus.postValue(Resource.error("The passwords do not match", null))
            return
        }
        viewModelScope.launch {
        val result = authRepository.registerUser(username, password)
            _registerStatus.postValue(result)
        }.invokeOnCompletion {

        }
    }

    @Inject
    lateinit var basicAuthInterceptor: BasicAuthInterceptor


    fun authenticateApi(username: String, password: String) {
        basicAuthInterceptor.username = username
        basicAuthInterceptor.password = password
    }

    fun isLoggedIn()= userDataRepository.isLoggedIn()


    init {
//        Firebase.auth.addAuthStateListener {
//            _signedIn.value = it.currentUser != null
//            _user.value = it.currentUser?.email ?: "Guest"
//        }
            _signedIn.value = userDataRepository.isLoggedIn()
            _user.value = _currentUsername.value
    }


    fun removeError() {
        _error.value = null
    }

    fun signOut() {
        viewModelScope.launch {
            Firebase.auth.signOut()
            userDataRepository.clearUserData()
            _signedIn.value = false
        }
    }

    fun loginWithGoogle(idToken: String) = viewModelScope.launch {
//        try {
//            app.connectedOrThrow()
//            _loading.postValue(true)
//            authRepository.loginWithGoogle(idToken)
//            _signedIn.postValue(true)
//        } catch (e: Exception) {
//            _error.postValue(e.localizedMessage)
//        } finally {
//            _loading.postValue(false)
//        }
    }
}