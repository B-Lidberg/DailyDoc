package com.lid.dailydoc.viewmodels

import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.dailydoc.data.remote.BasicAuthInterceptor
import com.lid.dailydoc.data.repository.AuthRepository
import com.lid.dailydoc.data.repository.UserDataRepository
import com.lid.dailydoc.navigation.UiDrawerState
import com.lid.dailydoc.other.Constants.NO_PASSWORD
import com.lid.dailydoc.other.Constants.NO_USERNAME
import com.lid.dailydoc.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataRepository: UserDataRepository,
    private val basicAuthInterceptor: BasicAuthInterceptor,
) : ViewModel() {

    private val userDataFlow = userDataRepository.userDataFlow


    val userData = userDataFlow.asLiveData()
    val currentUsername = userDataFlow.map { user ->
        user.username
    }.asLiveData()

    fun isLoggedIn(): Boolean {
        val username = userData.value?.username
        val password = userData.value?.password
        return  username != NO_USERNAME &&
                !username.isNullOrEmpty() &&
                password != NO_PASSWORD &&
                !password.isNullOrEmpty()
    }


    fun setLoginBoolean() {
        val username = userData.value?.username
        val password = userData.value?.password
        _signedIn.value =
            username != NO_USERNAME &&
            !username.isNullOrEmpty() &&
            password != NO_PASSWORD &&
            !password.isNullOrEmpty()
    }


    private val _loginStatus = MutableLiveData<Resource<String>>()
    val loginStatus: LiveData<Resource<String>> = _loginStatus

    private val _registerStatus = MutableLiveData<Resource<String>>()
    val registerStatus: LiveData<Resource<String>> = _registerStatus

    internal var subContentState: ((UiDrawerState) -> Unit)? = null

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _signedIn: MutableLiveData<Boolean> = MutableLiveData()
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
        }
    }
    fun authenticateApi(username: String, password: String) {
        basicAuthInterceptor.username = username
        basicAuthInterceptor.password = password
    }

    fun setUserData(username: String, password: String) {
        viewModelScope.launch {
            userDataRepository.setUserData(username, password)
        }.invokeOnCompletion {
            setLoginBoolean()
        }
    }


    init {
        viewModelScope.launch {
            setLoginBoolean()
        }
//        Firebase.auth.addAuthStateListener {
//            _signedIn.value = it.currentUser != null
//            _user.value = it.currentUser?.email ?: "Guest"
//        }
    }


    fun removeError() {
        _error.value = null
    }

    fun signOut() {
        viewModelScope.launch {
            Firebase.auth.signOut()
            userDataRepository.clearUserData()
        }.invokeOnCompletion {
            setLoginBoolean()

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