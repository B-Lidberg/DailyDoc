package com.lid.dailydoc.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id") val id: Long = 0L,
    @ColumnInfo(name = "note_date") var dateCreated: String,
    @ColumnInfo(name = "note_summary") var summary: String,
    @ColumnInfo(name = "note_body") var body: String,
    @ColumnInfo(name = "note_survey1") var survey1: String = "",
    @ColumnInfo(name = "note_survey2") var survey2: String = "",
    @ColumnInfo(name = "note_survey3") var survey3: String = "",
)


