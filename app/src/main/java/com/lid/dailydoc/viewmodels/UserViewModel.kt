package com.lid.dailydoc.viewmodels

import androidx.lifecycle.*
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.dailydoc.data.remote.BasicAuthInterceptor
import com.lid.dailydoc.data.repository.AuthRepository
import com.lid.dailydoc.data.repository.NoteRepositoryImpl
import com.lid.dailydoc.data.repository.UserDataRepository
import com.lid.dailydoc.other.Constants.NO_PASSWORD
import com.lid.dailydoc.other.Constants.NO_USERNAME
import com.lid.dailydoc.other.Resource
import com.lid.dailydoc.utils.LoadingState
import com.lid.dailydoc.utils.await
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataRepository: UserDataRepository,
    private val noteRepository: NoteRepositoryImpl,
    private val basicAuthInterceptor: BasicAuthInterceptor,
) : ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    private val userDataFlow = userDataRepository.userDataFlow

    val userData = userDataFlow.asLiveData()

    val currentUsername = userDataFlow.map { user ->
        user.username
    }.asLiveData()

    val currentPassword = userDataFlow.map { user ->
        user.password
    }.asLiveData()

    val currentUiDrawerState = userDataFlow.map { user ->
        user.uiDrawerState
    }.asLiveData()

    private val _signedIn: MutableLiveData<Boolean> = MutableLiveData()
    val signedIn: LiveData<Boolean> = _signedIn

    fun navigateToUserScreen() {
        viewModelScope.launch {
            userDataRepository.setUiDrawerStateToLoggedIn()
        }
    }

    fun navigateToLoginScreen() {
        viewModelScope.launch {
            userDataRepository.setUiDrawerStateToLoggedOut()
        }
    }

    fun navigateToRegisterScreen() {
        viewModelScope.launch {
            userDataRepository.setUiDrawerStateToRegister()
        }
    }

    fun navigateToInfoScreen() {
        viewModelScope.launch {
            userDataRepository.setUiDrawerStateToInfo()
        }
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
        }
        setLoginBoolean()
        _registerStatus.postValue(Resource.loading("default"))
        _loginStatus.postValue(Resource.loading("default"))
    }

    fun signOut() {
        viewModelScope.launch {
            noteRepository.clearLocalDatabase()
            userDataRepository.clearUserData()
            _registerStatus.postValue(Resource.loading(null))
            _loginStatus.postValue(Resource.loading(null))
            authenticateApi("", "")
        }
        setLoginBoolean()
        navigateToLoginScreen()
    }

    fun loginWithGoogle(credential: AuthCredential) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }
}