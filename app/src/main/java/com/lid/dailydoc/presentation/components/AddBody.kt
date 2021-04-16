package com.lid.dailydoc.presentation.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.data.model.arrow
import com.lid.dailydoc.data.model.bullet
import com.lid.dailydoc.data.model.complete
import com.lid.dailydoc.data.model.star

@ExperimentalAnimationApi
@Composable
fun AddBody(
    bodyChange: (String) -> Unit,
    body: String,
) {
    var isFocused by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    var textFieldValue by  remember { mutableStateOf(TextFieldValue(body)) }
    Row {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .background(colors.background)
                .weight(1f)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = !isFocused
                },
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                bodyChange(it.text)
            },
            label = { Text(text = "Note Body") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colors.background,
                unfocusedBorderColor = colors.background
            ),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),

        )
        AnimatedVisibility(
                visible = isFocused,
        ) {

            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Button(
                    modifier = Modifier.size(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colors.background
                    ),
                    onClick = {
                        textFieldValue = TextFieldValue("${textFieldValue.text}$star ")
                        focusRequester.requestFocus()
                        textFieldValue = textFieldValue.copyWithCursorPosition(CursorPosition.End)
                    },
                ) {
                    Text(star)
                }
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                Button(
                    modifier = Modifier.size(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colors.background
                    ),
                    onClick = {
                        textFieldValue = TextFieldValue("${textFieldValue.text} $complete ")
                        focusRequester.requestFocus()
                        textFieldValue = textFieldValue.copyWithCursorPosition(CursorPosition.End)
                    },
                ) {
                    Text(complete)
                }
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                Button(
                    modifier = Modifier.size(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colors.background
                    ),
                    onClick = {
                        textFieldValue = TextFieldValue("${textFieldValue.text}$arrow ")
                        focusRequester.requestFocus()
                        textFieldValue = textFieldValue.copyWithCursorPosition(CursorPosition.End)
                    },
                ) {
                    Text(arrow)
                }
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                Button(
                    modifier = Modifier.size(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colors.background
                    ),
                    onClick = {
                        textFieldValue = TextFieldValue("${textFieldValue.text}  $bullet ")
                        focusRequester.requestFocus()
                        textFieldValue = textFieldValue.copyWithCursorPosition(CursorPosition.End)
                    },
                ) {
                    Text(
                        text = bullet,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }

}