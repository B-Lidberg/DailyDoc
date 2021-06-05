package com.lid.dailydoc.other

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    // Symbols
    const val BULLET = "•"
    const val ARROW = "‣"
    const val STAR = "★"
    const val COMPLETE = "✅"
    const val CROSS = "❌"

    // Database
    const val DATABASE_NAME = "note_database"

    // URL
    const val BASE_URL = "http://10.0.2.2:8080"
    val IGNORE_AUTH_URLS = listOf("/login", "/register")

    // DataStore
    object DataStore {
        val DATA = stringPreferencesKey("data")
        val SECURED_DATA = stringPreferencesKey("secured_data")
    }

    const val USER_DATASTORE_NAME = "user_data"
    const val USER_DATASTORE_FILE_NAME = "user_data.pb"

    val KEY_USERNAME = stringPreferencesKey("KEY_USERNAME")
    const val NO_USERNAME =  "NO_USERNAME"

    val KEY_PASSWORD = stringPreferencesKey("KEY_LOGGED_IN_PASSWORD")
    const val NO_PASSWORD =  "NO_PASSWORD"








}