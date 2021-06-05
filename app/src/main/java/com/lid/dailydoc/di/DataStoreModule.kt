package com.lid.dailydoc.di

import android.content.Context
import com.lid.dailydoc.data.repository.UserDataRepository
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
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context): UserDataRepository =
        UserDataRepository(context)

}