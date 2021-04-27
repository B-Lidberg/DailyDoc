package com.lid.dailydoc.presentation.components.addscreen_components

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

sealed class CursorPosition {
    object Start : CursorPosition()
    object End : CursorPosition()
    data class Cursor(val loc: Int) : CursorPosition()
    data class Selection(val start: Int, val end: Int) : CursorPosition()
}


fun TextFieldValue.copyWithCursorPosition(cursorPosition: CursorPosition): TextFieldValue {
    return when (cursorPosition) {
        CursorPosition.Start -> copy(selection = TextRange(0))
        CursorPosition.End -> copy(selection = TextRange(text.length))
        is CursorPosition.Cursor -> copy(
            selection = TextRange(cursorPosition.loc.coerceIn(0..text.length))
        )
        is CursorPosition.Selection -> copy(
            selection = TextRange(
                start = cursorPosition.start.coerceIn(0..text.length),
                end = cursorPosition.end.coerceIn(0..text.length)
            )
        )
    }
}