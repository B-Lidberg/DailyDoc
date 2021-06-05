package com.lid.dailydoc.utils

/**
 * https://proandroiddev.com/securing-androids-datastore-ad56958ca6ee
 */

/*
class DataStoreUtil
@Inject
constructor(
    private val dataStore: DataStore<Preferences>,
    private val security: SecurityUtil
) {
    private val securityKeyAlias = "data-store"
    private val bytesToStringSeparator = "|"

    fun getData() = dataStore.data
        .map { preferences ->
            preferences[DATA].orEmpty()
        }

    suspend fun setData(value: String) {
        dataStore.edit {
            it[DATA] = value
        }
    }

    fun getSecuredData() = dataStore.data
        .secureMap<String> { preferences ->
            preferences[SECURED_DATA].orEmpty()
        }

    suspend fun setSecuredData(value: String) {
        dataStore.secureEdit(value) { prefs, encryptedValue ->
            prefs[SECURED_DATA] = encryptedValue
        }
    }

    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }

    private inline fun <reified T> Flow<Preferences>.secureMap(crossinline fetchValue: (value: Preferences) -> String): Flow<T> {
        return map {
            val decryptedValue = security.decryptData(
                securityKeyAlias,
                fetchValue(it).split(bytesToStringSeparator).map { it.toByte() }.toByteArray()
            )
            Json { encodeDefaults = true }.decodeFromString(decryptedValue)
        }
    }

    private suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
        value: T,
        crossinline editStore: (MutablePreferences, String) -> Unit
    ) {
        edit {
            val encryptedValue = security.encryptData(securityKeyAlias, Json.encodeToString(value))
            editStore.invoke(it, encryptedValue.joinToString(bytesToStringSeparator))
        }
    }
}
*/























