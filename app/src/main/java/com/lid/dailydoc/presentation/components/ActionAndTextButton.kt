package com.lid.dailydoc.presentation.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun CustomButton(onClick: () -> Unit, text: String) {
    Button(onClick = onClick) {
        Text(text)
    }
}