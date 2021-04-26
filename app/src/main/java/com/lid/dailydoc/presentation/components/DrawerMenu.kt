package com.lid.dailydoc.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.lid.dailydoc.data.model.Note
import kotlinx.coroutines.launch

@Composable
fun DrawerMenu(drawerState: DrawerState, notes: List<Note>) {
    val scope = rememberCoroutineScope()
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
        IconButton(onClick = {
                scope.launch {
                    drawerState.close()
                }
            }
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "close drawer")
        }
            Spacer(modifier = Modifier.padding(end = 12.dp))
            Text(text = "Information", modifier = Modifier.weight(1f), fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.padding(24.dp))
        Text("User: ${FirebaseAuth.getInstance().currentUser?.email ?: "Guest"}")
        Text("Total Notes: ${notes.size}")
    }
}