package com.lid.dailydoc.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.presentation.components.*
import com.lid.dailydoc.presentation.components.addscreen_components.AddBody
import com.lid.dailydoc.presentation.components.addscreen_components.AddSummary
import com.lid.dailydoc.presentation.components.addscreen_components.ClearButton
import com.lid.dailydoc.presentation.components.addscreen_components.SurveyBar
import com.lid.dailydoc.presentation.viewmodels.NoteAddViewModel
import com.lid.dailydoc.utils.getCurrentDateAsString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteAddScreen(
    vm: NoteAddViewModel,
    toMain: () -> Unit,
    note: Note,
) {

    val summary by vm.summary.observeAsState(note.summary)
    val body by vm.body.observeAsState(note.body)
    val survey1 by vm.survey1.observeAsState(note.survey1)
    val survey2 by vm.survey2.observeAsState(note.survey2)
    val survey3 by vm.survey3.observeAsState(note.survey3)

    val completeNote = Note(dateCreated = note.dateCreated, note.id, summary = summary, body = body,
                survey1 = survey1, survey2 = survey2, survey3 = survey3,
    )

    var expandedSurveyBar by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberLazyListState()

    val clearOnDateChange = { if (note.dateCreated != getCurrentDateAsString()) vm.clearNote() }
    val clear = { vm.clearNote() }

    Scaffold(
        topBar = {
            HeaderDateBar(
                vm.cachedNote.dateCreated,
                clear
            )
        },
        floatingActionButton = {
            SaveButton(
                { vm.addNote(it) },
                toMain,
                clearOnDateChange,
                completeNote
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = scrollState,
            ) {

                item {
                    SurveyBar(
                        expanded = expandedSurveyBar == summary,
                        onClick = {
                            expandedSurveyBar =
                                if (expandedSurveyBar == summary) null else summary
                        },
                        { vm.onSurvey1Change(it) },
                        { vm.onSurvey2Change(it) },
                        { vm.onSurvey3Change(it) },
                        survey1 = survey1,
                        survey2 = survey2,
                        survey3 = survey3,
                    )
                }
                item { AddSummary( { vm.onSummaryChange(it) }, summary) }

                item { AddBody( { vm.onBodyChange(it) }, body) }
            }
        }
    )
}

@ObsoleteCoroutinesApi
@Composable
fun SaveButton(
    addNote: (Note) -> Unit,
    toMain: () -> Unit,
    clear: () -> Unit,
    note: Note,
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor =
                if (note.summary.isEmpty()) Color.LightGray else MaterialTheme.colors.secondary
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .width(100.dp),
        enabled = (note.summary.isNotEmpty()),
        onClick = {
            addNote(note)
            clear.invoke()
            toMain.invoke()
        },
    ) {
        Text(
            text = "Save",
            color = MaterialTheme.colors.onPrimary,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HeaderDateBar(
    date: String,
    onClear: () -> Unit,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier.padding(bottom = 6.dp, start = 8.dp, end = 8.dp)
    ) {
        CustomTopBar(date) { ClearButton(onClear) }
    }
}



