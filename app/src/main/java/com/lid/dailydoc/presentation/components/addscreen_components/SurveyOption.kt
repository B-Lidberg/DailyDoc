package com.lid.dailydoc.presentation.components.addscreen_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SurveyButtonAndOptions(
    onSurveyChange: (String) -> Unit,
    savedAnswer: String,
    survey: List<String>
) {
    onSurveyChange(savedAnswer)

    Text(survey[0], textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.size(4.dp))
    val surveyAnswers = survey.drop(1)

    val answerSets = if (surveyAnswers.size != 4) {
        listOf(surveyAnswers)
    } else {
        listOf(
            listOf(surveyAnswers[0], surveyAnswers[1]),
            listOf(surveyAnswers[2], surveyAnswers[3])
        )
    }
    Column {
        answerSets.forEach { answerSet ->
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                answerSet.forEach { surveyAnswer ->
                    Button(
                        modifier = Modifier.padding(bottom = 4.dp),
                        onClick = { onSurveyChange(surveyAnswer) },
                        shape = CircleShape,
                        colors = surveyButtonBackground(savedAnswer, surveyAnswer),
                    ) {
                        SurveyButtonText(savedAnswer, surveyAnswer)
                    }
                }
            }
        }
    }
}

@Composable
fun SurveyButtonText(savedAnswer: String, currentAnswer: String) {
    Text(
        currentAnswer, color = if (savedAnswer == currentAnswer) {
            MaterialTheme.colors.onPrimary
        } else {
            MaterialTheme.colors.onBackground
        }
    )
}

@Composable
fun surveyButtonBackground(savedAnswer: String, currentAnswer: String): ButtonColors =
    ButtonDefaults.buttonColors(
        backgroundColor = if (savedAnswer == currentAnswer) {
            MaterialTheme.colors.secondaryVariant
        } else {
            MaterialTheme.colors.background
        }
    )
