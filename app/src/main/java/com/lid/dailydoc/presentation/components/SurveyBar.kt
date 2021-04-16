package com.lid.dailydoc.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.presentation.viewmodels.NoteAddViewModel

@ExperimentalAnimationApi
@Composable
fun SurveyBar(
    vm: NoteAddViewModel,
    expanded: Boolean,
    onClick: () -> Unit,
    survey1: String,
    survey2: String,
    survey3: String,
) {
    SurveyBarSpacer(visible = expanded)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .animateContentSize(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row {
                if (!expanded) {
                    ExpandedIcon()
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Expand Survey",
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    ExpandedIcon()
                }
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))

                Survey(vm = vm,
                    survey1 = survey1,
                    survey2 = survey2,
                    survey3 = survey3,
                )
            }
        }
    }
    SurveyBarSpacer(visible = expanded)
}

@Composable
fun ExpandedIcon() {
    Icon(
        imageVector = Icons.Filled.KeyboardArrowDown,
        contentDescription = "Expanded Icon",
    )
}

@ExperimentalAnimationApi
@Composable
fun SurveyBarSpacer(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}