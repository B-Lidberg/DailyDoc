package com.lid.dailydoc.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.data.survey1Data
import com.lid.dailydoc.data.survey2Data
import com.lid.dailydoc.data.survey3Data
import com.lid.dailydoc.presentation.components.survey_components.FourOptions
import com.lid.dailydoc.presentation.components.survey_components.ThreeOptions
import com.lid.dailydoc.presentation.components.survey_components.TwoOptions

@Composable
fun Survey(
    onSurvey1Change: (String) -> Unit,
    onSurvey2Change: (String) -> Unit,
    onSurvey3Change: (String) -> Unit,
    survey1: String,
    survey2: String,
    survey3: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TwoOptions(
            { onSurvey1Change(it) },
            savedAnswer = survey1,
            survey = survey1Data
        )
        Spacer(modifier = Modifier.size(4.dp))

        ThreeOptions(
            { onSurvey2Change(it) },
            savedAnswer = survey2,
            survey = survey2Data
        )
        Spacer(modifier = Modifier.size(4.dp))

        FourOptions(
            { onSurvey3Change(it) },
            savedAnswer = survey3,
            survey = survey3Data
        )
    }
}