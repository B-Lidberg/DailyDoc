package com.lid.dailydoc.data.model

import androidx.room.*
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import java.util.*

@Entity(tableName = "notes_table")
data class Note(
    @ColumnInfo(name = "note_date") var date: Long,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "note_id") val noteId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "note_summary") var summary: String = "",
    @ColumnInfo(name = "note_body") var body: String = "",
    @ColumnInfo(name = "note_survey1") var survey1: String = "",
    @ColumnInfo(name = "note_survey2") var survey2: String = "",
    @ColumnInfo(name = "note_survey3") var survey3: String = "",
    @ColumnInfo(name = "note_owner") var owner: String = "",
    @ColumnInfo(name = "note_users") val users: List<String> = emptyList(),
    @Expose(deserialize = false, serialize = false)
    var isSynced: Boolean = false,
)


