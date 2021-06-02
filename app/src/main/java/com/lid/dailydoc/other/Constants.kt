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

    const val KEY_LOGGED_IN_EMAIL = "KEY_LOGGED_IN_EMAIL"
    const val KEY_PASSWORD = "KEY_PASSWORD"

    const val NO_EMAIL = "NO_EMAIL"
    const val NO_PASSWORD = "NO_PASSWORD"








}