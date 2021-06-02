package com.lid.dailydoc.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface NoteApi {

    @POST("/register")
    suspend fun register(

    )

}