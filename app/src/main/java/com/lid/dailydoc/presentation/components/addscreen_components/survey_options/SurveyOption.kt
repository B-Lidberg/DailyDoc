package com.lid.dailydoc.presentation.components.addscreen_components.survey_options

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SurveyButtonAndOptions(
    onSurveyChange: (String) -> Unit,
    savedAnswer: String,
    survey: List<String>
) {
    onSurveyChange(savedAnswer)

    Text(survey[0])
    Spacer(modifier = Modifier.size(4.dp))
    val surveyAnswers = survey.drop(1)

    if (surveyAnswers.size != 4) {
        Row {
            surveyAnswers.forEach { surveyAnswer ->
                OutlinedButton(
                    modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
                    onClick = { onSurveyChange(surveyAnswer) },
                    shape = CircleShape,
                    colors = surveyButtonBackground(savedAnswer, surveyAnswer),
                ) {
                    SurveyText(savedAnswer, surveyAnswer)
                }
            }
        }
    } else {
        val answerSets = listOf(
            listOf(surveyAnswers[0], surveyAnswers[1]),
            listOf(surveyAnswers[2], surveyAnswers[3]),
        )
        Column {
            answerSets.forEach { answerSet ->
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    answerSet.forEach { surveyAnswer ->
                        OutlinedButton(
                            modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
                            onClick = { onSurveyChange(surveyAnswer) },
                            shape = CircleShape,
                            colors = surveyButtonBackground(savedAnswer, surveyAnswer),
                        ) {
                            SurveyText(savedAnswer, surveyAnswer)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SurveyText(savedAnswer: String, currentAnswer: String) {
    Text(currentAnswer, color = if (savedAnswer == currentAnswer) {
        MaterialTheme.colors.onPrimary
    } else {
        MaterialTheme.colors.onBackground
    })
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