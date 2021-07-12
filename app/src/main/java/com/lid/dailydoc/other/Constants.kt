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
//    const val BASE_URL = "http://10.0.2.2:8843"
    const val BASE_URL = "http://192.168.1.202:8843"

    val IGNORE_AUTH_URLS = listOf("/login", "/register")

    // Navigation Routes
    const val NOTES = "notes"
    const val NOTE_DETAILS = "note_details"
    const val NOTE_ID = "noteId"
    const val NOTE_KEY = "note"
    const val SPLASH = "loading"

    // DataStore
    object DataStore {
        val DATA = stringPreferencesKey("data")
        val SECURED_DATA = stringPreferencesKey("secured_data")
    }

    const val USER_DATASTORE_NAME = "user_data"
    const val USER_DATASTORE_FILE_NAME = "user_data.pb"

    val KEY_USERNAME = "username"
    const val NO_USERNAME =  "NO_USERNAME"

    val KEY_PASSWORD = "password"
    const val NO_PASSWORD =  "NO_PASSWORD"








}