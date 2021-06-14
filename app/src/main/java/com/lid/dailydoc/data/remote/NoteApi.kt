package com.lid.dailydoc.data.remote

import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.data.remote.requests.AccountRequest
import com.lid.dailydoc.data.remote.requests.AddUserRequest
import com.lid.dailydoc.data.remote.requests.DeleteNoteRequest
import com.lid.dailydoc.data.remote.responses.SimpleResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NoteApi {

    @POST("/register")
    suspend fun register(
        @Body registerRequest: AccountRequest
    ): Response<SimpleResponse>

    @POST("/login")
    suspend fun login(
        @Body loginRequest: AccountRequest
    ): Response<SimpleResponse>

    @POST("/addNote")
    suspend fun addNote(
        @Body note: Note
    ): Response<ResponseBody>

    @POST("/deleteNote")
    suspend fun deleteNote(
        @Body deleteNoteRequest: DeleteNoteRequest
    ): Response<ResponseBody>

    @POST("/addUserToNote")
    suspend fun addUserToNote(
        @Body addUserRequest: AddUserRequest
    ): Response<SimpleResponse>

    @GET("/getNotes")
    suspend fun getNotes(): Response<List<Note>>
}