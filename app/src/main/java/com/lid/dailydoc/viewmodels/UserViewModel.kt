package com.lid.dailydoc.viewmodels

import androidx.lifecycle.*
import com.lid.dailydoc.UserData
import com.lid.dailydoc.data.remote.BasicAuthInterceptor
import com.lid.dailydoc.data.repository.AuthRepository
import com.lid.dailydoc.data.repository.UserDataRepository
import com.lid.dailydoc.other.Constants.NO_PASSWORD
import com.lid.dailydoc.other.Constants.NO_USERNAME
import com.lid.dailydoc.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
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

    fun navigateToLoadingScreen() {
        viewModelScope.launch {
            userDataRepository.setUiDrawerStateToLoading()
        }
    }

    fun redirectScreen() {
        viewModelScope.launch {
            if (signedIn.value == true) {
                userDataRepository.setUiDrawerStateToLoggedIn()
            } else {
                userDataRepository.setUiDrawerStateToLoggedOut()
            }
        }
    }

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

//    suspend fun isAuthenticated(userData: LiveData<UserData>): Boolean {
//        val username = userData.value?.username ?: ""
//        val password = userData.value?.password ?: ""
//        if ()
//    }


    private val _loginStatus = MutableLiveData<Resource<String>>()
    val loginStatus: LiveData<Resource<String>> = _loginStatus

    private val _registerStatus = MutableLiveData<Resource<String>>()
    val registerStatus: LiveData<Resource<String>> = _registerStatus

    internal var subContentState: ((UserData.UiDrawerState) -> Unit)? = null

    private val _uiDrawerState = MutableLiveData<UserData.UiDrawerState>()
    val uiDrawerState: LiveData<UserData.UiDrawerState> = _uiDrawerState

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
        _registerStatus.postValue(Resource.loading("default"))
    }

    fun signOut() {
//            Firebase.auth.signOut()
        viewModelScope.launch {
            userDataRepository.clearUserData()
            _registerStatus.postValue(Resource.loading(null))
            _loginStatus.postValue(Resource.loading(null))
        }
        setLoginBoolean()


    }

//    fun loginWithGoogle(idToken: String) = viewModelScope.launch {
//        try {
//            _loading.postValue(true)
//            authRepository.loginWithGoogle(idToken)
//            _signedIn.postValue(true)
//        } catch (e: Exception) {
//            _error.postValue(e.localizedMessage)
//        } finally {
//            _loading.postValue(false)
//        }
//    }
}