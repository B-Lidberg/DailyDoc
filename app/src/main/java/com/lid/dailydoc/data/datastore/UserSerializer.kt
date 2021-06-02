package com.lid.dailydoc.data.datastore

import android.content.Context
import android.service.autofill.UserData
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal val Context.settingsDataStore: DataStore<UserData> by dataStore(
    fileName = "user_pres.pb",
    serializer = UserSerializer
)

private object UserSerializer : Serializer<UserData> {
    override val defaultValue: UserData =
        UserData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserData {
        try {
            return UserSerializer.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserData, output: OutputStream) = t.writeTo(output)
    }
}