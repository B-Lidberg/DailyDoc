package com.lid.dailydoc.data.remote.requests

data class AddUserRequest(
    val owner: String,
    val noteId: String
)