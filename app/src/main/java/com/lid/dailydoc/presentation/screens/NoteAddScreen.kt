package com.lid.dailydoc.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.presentation.components.CustomTopBar
import com.lid.dailydoc.presentation.components.addscreen_components.BodyField
import com.lid.dailydoc.presentation.components.addscreen_components.ClearButton
import com.lid.dailydoc.presentation.components.addscreen_components.SummaryField
import com.lid.dailydoc.presentation.components.addscreen_components.SurveyBar
import com.lid.dailydoc.utils.getCurrentDateAsLong
import com.lid.dailydoc.utils.getDateAsString
import com.lid.dailydoc.viewmodels.NoteAddViewModel
import kotlinx.coroutines.*

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

    val owner by vm.currentUsername.observeAsState(vm.currentUsername.value ?: "")
    val summary by vm.summary.observeAsState(note.summary)
    val body by vm.body.observeAsState(note.content)
    val survey1 by vm.survey1.observeAsState(note.survey1)
    val survey2 by vm.survey2.observeAsState(note.survey2)
    val survey3 by vm.survey3.observeAsState(note.survey3)

    val completeNote = Note(
        date = note.date, noteId = note.noteId, summary = summary, content = body,
        survey1 = survey1, survey2 = survey2, survey3 = survey3, owner = owner
    )

    var expandedSurveyBar by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberLazyListState()

    val clearOnDateChange = { if (note.date != getCurrentDateAsLong()) vm.clearNote() }
    val clear = { vm.clearNote() }

    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        topBar = {
            HeaderDateBar(
                note.date,
                clear
            )
        },
        floatingActionButton = {
            SaveButton(
                { vm.addNote(it) },
                toMain,
                clearOnDateChange,
                completeNote,
                snackbarHostState,
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
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
                item { EmptySummarySnackBar(snackbarHostState) }

                item { SummaryField({ vm.onSummaryChange(it) }, summary) }

                item { BodyField({ vm.onBodyChange(it) }, body) }
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
    snackbarHostState: SnackbarHostState,
) {
    FloatingActionButton(
        onClick = {
            if (note.summary.isNotEmpty()) {
                addNote(note)
                clear.invoke()
                toMain.invoke()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    snackbarHostState.showSnackbar(
                        message = "Summary cannot be empty!",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        },
        backgroundColor =
        if (note.summary.isEmpty()) {
            MaterialTheme.colors.background
        } else {
            MaterialTheme.colors.secondary
        },
        modifier =
        Modifier
            .size(width = 90.dp, height = 45.dp)
    ) {
        Text(
            text = "Save",
            color = MaterialTheme.colors.onSecondary,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HeaderDateBar(
    date: Long,
    onClear: () -> Unit,
) {
    val headerDate = getDateAsString(date)
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier.padding(bottom = 6.dp, start = 8.dp, end = 8.dp)
    ) {
        CustomTopBar(headerDate) { ClearButton(onClear) }
    }
}

@Composable
fun EmptySummarySnackBar(snackbarHostState: SnackbarHostState) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = {
            Snackbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .padding(bottom = 8.dp),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
            ) {
                Text(
                    text = snackbarHostState.currentSnackbarData?.message ?: "",
                    style = MaterialTheme.typography.body1, textAlign = TextAlign.Center
                )
            }
        }
    )
}



