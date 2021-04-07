package com.lid.dailydoc.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.lid.dailydoc.presentation.viewmodels.NoteAddViewModel

@Composable
fun AddSummary(
    vm: NoteAddViewModel,
    summary: String,
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = summary,
        onValueChange = { vm.onSummaryChange(it) },
        label = { Text(text = "Note Summary") },
        placeholder = {  Text(text = "Overview", Modifier.alpha(.25f))},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor =  MaterialTheme.colors.primary, unfocusedBorderColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    )
}