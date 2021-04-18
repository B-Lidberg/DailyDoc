package com.lid.dailydoc.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.presentation.components.*
import com.lid.dailydoc.presentation.components.addscreen_components.BodyField
import com.lid.dailydoc.presentation.components.addscreen_components.SummaryField
import com.lid.dailydoc.presentation.components.addscreen_components.ClearButton
import com.lid.dailydoc.presentation.components.addscreen_components.SurveyBar
import com.lid.dailydoc.viewmodels.NoteAddViewModel
import com.lid.dailydoc.utils.getCurrentDateAsString
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

    val snackbarHostState = remember { SnackbarHostState() }

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
                item {
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
                                    style = MaterialTheme.typography.body1, textAlign = TextAlign.Center)
                            }
                        }
                    )
                }
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
        contentColor =
            contentColorFor(backgroundColor =
                if (note.summary.isEmpty()) Color.LightGray else MaterialTheme.colors.secondary
        ),
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



