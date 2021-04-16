package com.lid.dailydoc.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomTopBar(headerText: String, button: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp, top = 4.dp)
                .weight(1f),
            text = headerText,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
        )
        button()

    }
}