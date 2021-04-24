package com.lid.dailydoc.data.extras

import com.lid.dailydoc.data.model.Note

val fakeNote = Note(
    dateCreated = "Saturday Jan 1, 2000",
    id = -1,
    summary = "Sample summary! Sum up your daily notes here",
    body = "Body goes here! Expand on summary information and include self comments. " +
            "Find source code at: https://github.com/B-Lidberg/DailyDoc",
    survey1 = "Yes!",
    survey2 = "Downloaded Daily Doc, of course!",
    survey3 = "Pushed and Pushed!"
)