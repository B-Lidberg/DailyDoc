package com.lid.dailydoc.presentation.components.addscreen_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization

@Composable
fun AddSummary(
    summaryChange: (String) -> Unit,
    summary: String,
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = summary,
        onValueChange = { summaryChange(it) },
        label = { Text(text = "Note Summary") },
        placeholder = {  Text(text = "Overview", Modifier.alpha(.25f))},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor =  MaterialTheme.colors.primary, unfocusedBorderColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    )
}