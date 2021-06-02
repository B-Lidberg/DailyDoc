package com.lid.dailydoc.data.repository

import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.dailydoc.data.remote.NoteApi
import com.lid.dailydoc.data.remote.requests.AccountRequest
import com.lid.dailydoc.other.Resource
import com.lid.dailydoc.utils.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val noteApi: NoteApi
) {

    suspend fun loginWithGoogle(idToken: String): Unit = withContext(IO) {
        Firebase.auth
            .signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
            .await()
    }

    suspend fun loginWithUsername(username: String, password: String) = withContext(IO) {
        try {
            val response = noteApi.login(AccountRequest(username, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Resource.success(response.body()?.message)
            } else {
                Resource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't connect to the server. Check your internet connection", null)
        }
    }

    suspend fun registerUser(username: String, password: String) = withContext(IO) {
        try {
            val response = noteApi.register(AccountRequest(username, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Resource.success(response.body()?.message)
            } else {
                Resource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't connect to the server. Check your internet connection", null)
        }
    }
}