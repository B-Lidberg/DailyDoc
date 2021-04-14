package com.lid.dailydoc.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lid.dailydoc.data.model.Note


@Composable
fun NoteCard(note: Note, toDetails: (Long) -> Unit) {
    val noteId = note.id
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
            .height(80.dp)
            .clip(shape = CutCornerShape(topEnd = 40.dp, bottomEnd = 40.dp))
            .clickable(onClick = { toDetails(noteId) } ),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    text = note.dateCreated,
                    fontSize = 20.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onPrimary
                )

            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = note.summary,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp, end = 30.dp),
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}
