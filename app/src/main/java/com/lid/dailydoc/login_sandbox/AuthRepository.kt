package com.lid.dailydoc.login_sandbox

import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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