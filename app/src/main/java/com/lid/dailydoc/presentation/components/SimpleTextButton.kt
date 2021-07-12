package com.lid.dailydoc.presentation.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleTextButton(
    onClick: () -> Unit,
    text: String,
    buttonModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = buttonModifier
    ) {
        Text(
            text,
            modifier = textModifier
        )
    }
}