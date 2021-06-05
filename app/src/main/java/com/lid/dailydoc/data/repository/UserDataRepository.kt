package com.lid.dailydoc.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.lid.dailydoc.other.Constants.USER_DATASTORE_NAME
import com.lid.dailydoc.other.Constants.KEY_PASSWORD
import com.lid.dailydoc.other.Constants.NO_PASSWORD
import com.lid.dailydoc.other.Constants.KEY_USERNAME
import com.lid.dailydoc.other.Constants.NO_USERNAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(USER_DATASTORE_NAME)

@Singleton
class UserDataRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private val userDataStore = context.dataStore

    private var curUsername: String? = null
    private var curPassword: String? = null

    fun isLoggedIn(): Boolean {
        userDataStore.data.map { user ->
            curUsername = user[KEY_USERNAME] ?: ""
            curPassword = user[KEY_PASSWORD] ?: ""
        }
        return !curUsername.isNullOrEmpty() && !curPassword.isNullOrEmpty()
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
    val username: Flow<String> = userDataStore.data.map { user ->
        user[KEY_USERNAME] ?: NO_USERNAME
    }
    val password: Flow<String> = userDataStore.data.map { user ->
        user[KEY_PASSWORD] ?: NO_PASSWORD
    }
}