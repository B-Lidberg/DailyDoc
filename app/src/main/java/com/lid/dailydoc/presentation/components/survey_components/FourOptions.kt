package com.lid.dailydoc.presentation.components.survey_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.presentation.viewmodels.NoteAddViewModel

@Composable
fun FourOptions(
    vm: NoteAddViewModel,
    savedAnswer: String,
    survey: List<String>
) {
    vm.onSurvey3Change(savedAnswer)

    Text(survey[0])
    Spacer(modifier = Modifier.size(4.dp))

    Column {

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)) {

            OutlinedButton(
                modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
                onClick = {
                    vm.onSurvey3Change(survey[1])

                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (savedAnswer == survey[1]) MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary),

                ) {
                Text(survey[1], color = if (savedAnswer == survey[1]) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary)
            }

            OutlinedButton(
                modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
                onClick = {
                    vm.onSurvey3Change(survey[2])

                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (savedAnswer == survey[2]) MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary),

                ) {
                Text(survey[2], color = if (savedAnswer == survey[2]) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary)
            }
        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            OutlinedButton(
                modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
                onClick = {
                    vm.onSurvey3Change(survey[3])

                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (savedAnswer == survey[3]) MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary),

                ) {
                Text(survey[3], color = if (savedAnswer == survey[3]) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary)
            }
            OutlinedButton(
                modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
                onClick = {
                    vm.onSurvey3Change(survey[4])

                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (savedAnswer == survey[4]) MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary),

                ) {
                Text(survey[4], color = if (savedAnswer == survey[4]) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary)
            }
        }
    }
}