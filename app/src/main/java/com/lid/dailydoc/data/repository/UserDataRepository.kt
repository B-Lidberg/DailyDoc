package com.lid.dailydoc.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.lid.dailydoc.UserData
import com.lid.dailydoc.other.Constants
import com.lid.dailydoc.other.Constants.NO_PASSWORD
import com.lid.dailydoc.other.Constants.NO_USERNAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    Constants.USER_DATASTORE_NAME
)

@Singleton
class UserDataRepository @Inject constructor(
    private val userDataStore: DataStore<UserData>
) {

    private val TAG: String = "UserDataRepo"

    val userDataFlow: Flow<UserData> = userDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading user data.", exception)
            } else {
                throw exception
            }
        }

    suspend fun setUserData(username: String, password: String) {
        userDataStore.updateData { currentUserData ->
            val currentUsername = currentUserData.username ?: NO_USERNAME
            val currentPassword = currentUserData.password ?: NO_PASSWORD
            val newUsername =
            if (currentUsername != NO_USERNAME && currentPassword != NO_PASSWORD) {
                username
            } else {
                currentUsername
            }
            val newPassword =
                if (currentUsername != NO_USERNAME && currentPassword != NO_PASSWORD) {
                password
            } else {
                currentPassword
            }
            currentUserData
                .toBuilder()
                .setUsername(newUsername)
                .setPassword(newPassword)
                .build()
        }
    }

    suspend fun clearUserData() {
        userDataStore.updateData { currentUserData ->
            currentUserData
                .toBuilder()
                .clear()
                .build()
        }
    }

}