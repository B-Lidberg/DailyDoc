package com.lid.dailydoc.data.extras

import com.lid.dailydoc.data.model.Note
import java.time.LocalDate

val fakeNote = Note(
    date = LocalDate.parse("2020-01-01").toEpochDay(),
    noteId = "1",
    summary = "Sample summary! Sum up your daily notes here",
    body = "Body goes here! Expand on summary information and include self comments. " +
            "Find source code at: https://github.com/B-Lidberg/DailyDoc",
    survey1 = "Yes!",
    survey2 = "Downloaded Daily Doc, of course!",
    survey3 = "Pushed and Pushed!"
)