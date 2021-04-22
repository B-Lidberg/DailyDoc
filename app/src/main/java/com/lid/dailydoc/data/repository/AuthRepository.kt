package com.lid.dailydoc.data.repository

import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.dailydoc.utils.await
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AuthRepository {

    suspend fun loginWithGoogle(idToken: String): Unit = withContext(IO) {
        Firebase.auth
            .signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
            .await()
    }

    companion object {
        @Volatile
        var INSTANCE: AuthRepository? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            val instance = AuthRepository()
            INSTANCE = instance
            return instance
        }
    }
}