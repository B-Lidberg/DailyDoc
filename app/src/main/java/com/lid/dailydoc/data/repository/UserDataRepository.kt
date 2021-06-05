package com.lid.dailydoc.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.lid.dailydoc.other.Constants
import com.lid.dailydoc.other.Constants.KEY_PASSWORD
import com.lid.dailydoc.other.Constants.NO_PASSWORD
import com.lid.dailydoc.other.Constants.KEY_USERNAME
import com.lid.dailydoc.other.Constants.NO_USERNAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    Constants.USER_DATASTORE_NAME
)

@Singleton
class UserDataRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private var _username: Flow<String> = flow { "" }
    private var _password: Flow<String> = flow { "" }

    val username = _username.asLiveData()
    val password = _password.asLiveData()

    init {
        _username = context.dataStore.data
            .map { user ->
                user[KEY_USERNAME] ?: ""
            }

        _password = context.dataStore.data
            .map { user ->
                user[KEY_USERNAME] ?: ""
            }
    }


    private val userDataStore = context.dataStore

    fun isLoggedIn(): Boolean {
        return !username.value.isNullOrEmpty() && !password.value.isNullOrEmpty()
    }

    suspend fun setUserData(username: String, password: String) {
        userDataStore.edit { user ->
            user[KEY_USERNAME] = username
            user[KEY_PASSWORD] = password
        }
    }

    suspend fun clearUserData() {
        userDataStore.edit { user ->
            user.clear()
        }
    }
}