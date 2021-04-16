package com.lid.dailydoc.presentation.components.survey_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TwoOptions(
    onSurveyChange: (String) -> Unit,
    savedAnswer: String,
    survey: List<String>
) {

    onSurveyChange(savedAnswer)

    Text(survey[0])
    Spacer(modifier = Modifier.size(4.dp))

    Row {
        OutlinedButton(
            onClick = {
                onSurveyChange(survey[1])
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (savedAnswer == survey[1]) MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary),

        ) {
            Text(survey[1], color = if (savedAnswer == survey[1]) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary)
        }

        Spacer(modifier = Modifier.width(25.dp))

        OutlinedButton(
            onClick = {
                onSurveyChange(survey[2])
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (savedAnswer == survey[2]) MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary),

        ) {
            Text(survey[2], color = if (savedAnswer == survey[2]) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary)
        }
    }
}