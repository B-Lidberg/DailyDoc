package com.lid.dailydoc.data.repository

import android.content.Context
import androidx.datastore.core.DataStore

class UserPreferencesRepository(
    private val userPreferencesStore: DataStore<UserPreferences>,
    context: Context
) {

    data class UserPreferences(val showCompleted: Boolean)


}