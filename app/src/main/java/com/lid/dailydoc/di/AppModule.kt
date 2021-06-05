package com.lid.dailydoc.di

import android.content.Context
import androidx.room.Room
import com.lid.dailydoc.NotesApplication
import com.lid.dailydoc.data.local.NoteDao
import com.lid.dailydoc.data.local.NoteDatabase
import com.lid.dailydoc.data.remote.BasicAuthInterceptor
import com.lid.dailydoc.data.remote.NoteApi
import com.lid.dailydoc.other.Constants.BASE_URL
import com.lid.dailydoc.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideApplication(
        @ApplicationContext app: Context
    ): NotesApplication {
        return app as NotesApplication
    }

    @Provides
    @Singleton
    fun provideBasicAuthInterceptor() = BasicAuthInterceptor()

    @Provides
    @Singleton
    fun provideNoteApi(
        okHttpClient: OkHttpClient.Builder,
        basicAuthInterceptor: BasicAuthInterceptor
    ) : NoteApi {
        val client = okHttpClient
            .addInterceptor(basicAuthInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient.Builder {
        val trustAllCertificates: Array<TrustManager> = arrayOf(
            object: X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    /* NO-OP */
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    /* NO-OP */
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCertificates, SecureRandom())

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCertificates[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
    }

}