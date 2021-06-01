package com.lid.dailydoc.data.model

import java.util.*

data class User(
    var userId: UUID,
    var name: String = "",
    var notes: List<Note>
)
