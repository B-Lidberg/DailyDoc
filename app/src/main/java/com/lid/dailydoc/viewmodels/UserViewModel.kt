package com.lid.dailydoc.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.dailydoc.data.repository.AuthRepository
import com.lid.dailydoc.navigation.UiDrawerState
import com.lid.dailydoc.utils.connectedOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val app: Application,
    private val authRepository: AuthRepository,
) : AndroidViewModel(app) {

    init {
        Firebase.auth.addAuthStateListener {
            _signedIn.value = it.currentUser != null
            _user.value = it.currentUser?.email ?: "Guest"
        }
    }

    internal var subContentState: ((UiDrawerState) -> Unit)? = null
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