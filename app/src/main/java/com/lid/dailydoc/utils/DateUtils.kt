package com.lid.dailydoc.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateAsString(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE MMM d, yyyy")
    return now.format(formatter)
}