package com.lid.dailydoc.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomTopBar(headerText: String, onAction: () -> Unit, icon: ImageVector) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 4.dp, top = 4.dp)
                .align(Alignment.CenterVertically)
                .weight(1f),
            text = headerText,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
        )
        IconButton(onClick = onAction) {
            Icon(icon, contentDescription = null)
        }
    }
}