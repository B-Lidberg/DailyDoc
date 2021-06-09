package com.lid.dailydoc.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import com.lid.dailydoc.UserData
import com.lid.dailydoc.datastore.data.UserDataSerializer
import com.lid.dailydoc.other.Constants.USER_DATASTORE_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun provideSerializer() = UserDataSerializer

    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context,
        serializer: UserDataSerializer
    ): DataStore<UserData> =
        DataStoreFactory.create(
            produceFile = { context.dataStoreFile(USER_DATASTORE_FILE_NAME) },
            serializer = serializer
        )

}