package com.lid.dailydoc.presentation.components.addscreen_components.survey_options

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThreeOptions(
    onSurveyChange: (String) -> Unit,
    savedAnswer: String,
    survey: List<String>
) {
    onSurveyChange(savedAnswer)

    Text(survey[0])
    Spacer(modifier = Modifier.size(4.dp))

    Row {
        OutlinedButton(
            modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
            onClick = {
                onSurveyChange(survey[1])
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (savedAnswer == survey[1]) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.background
            ),
        ) {
            Text(survey[1], color = if (savedAnswer == survey[1]) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground)
        }

        OutlinedButton(
            modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
            onClick = {
                onSurveyChange(survey[2])
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (savedAnswer == survey[2]) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.background
            ),

            ) {
            Text(survey[2], color = if (savedAnswer == survey[2]) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground)
        }

        OutlinedButton(
            modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
            onClick = {
                onSurveyChange(survey[3])

            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (savedAnswer == survey[3]) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.background
            ),

            ) {
            Text(survey[3], color = if (savedAnswer == survey[3]) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground)
        }
    }
}
