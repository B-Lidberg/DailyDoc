package com.lid.dailydoc.presentation.components.addscreen_components


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun BodyField(
    bodyChange: (String) -> Unit,
    body: String,
) {
    TextField(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(colors.background),
        value = body,
        onValueChange = { bodyChange(it) },
        label = { Text(text = "Note Body") },
        placeholder = { Text(text = "Comments and Reflection go here", Modifier.alpha(.25f)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colors.background,
            unfocusedBorderColor = colors.background
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
        )
    )
}
