package com.lid.dailydoc.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ProgressBar(visibility: Boolean) {

    if (visibility) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Loading...", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.padding(4.dp))
            CircularProgressIndicator(
                Modifier
                    .fillMaxSize(.15f)
            )
        }
    }
}