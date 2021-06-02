package com.lid.dailydoc.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateAsString(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE MMM d, yyyy")
    return now.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateAsLong(): Long {
    return LocalDate.now().toEpochDay()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateAsString(dateToConvert: Long): String {
    val now = LocalDate.ofEpochDay(dateToConvert)
    val formatter = DateTimeFormatter.ofPattern("EEEE MMM d, yyyy")
    return now.format(formatter)
}