package com.lid.dailydoc.datastore.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.lid.dailydoc.UserData
import java.io.InputStream
import java.io.OutputStream

object UserDataSerializer : Serializer<UserData> {

    override val defaultValue: UserData = UserData.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): UserData {
            try {
                return UserData.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto", exception)
            }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: UserData, output: OutputStream) = t.writeTo(output)

}