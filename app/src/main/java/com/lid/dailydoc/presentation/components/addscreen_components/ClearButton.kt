package com.lid.dailydoc.presentation.components.addscreen_components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.lid.dailydoc.presentation.components.SimpleTextButton

@Composable
fun ClearButton(onAction: () -> Unit) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false) }

            IconButton(onClick = { openDialog.value = true }) {
                Icon(Icons.Default.Clear, contentDescription = null)
            }

            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Are you sure?")
                    },
                    text = {
                        Text(
                            "If Confirmed the note will be cleared, " +
                                    "however an overwrite will not occur until Saved. " +
                                    "To retain previous state, press the back button or close the app"
                        )
                    },
                    confirmButton = {
                        SimpleTextButton(
                            onClick = {
                                onAction()
                                openDialog.value = false
                            },
                            text = "Confirm"
                        )
                    },
                    dismissButton = {
                        SimpleTextButton(
                            onClick = { openDialog.value = false },
                            text = "Dismiss"
                        )
                    }
                )
            }
        }
    }
}