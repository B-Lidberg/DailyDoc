package com.lid.dailydoc.presentation.components.addscreen_components


import androidx.compose.animation.*
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
import androidx.compose.ui.unit.dp
import com.lid.dailydoc.data.extras.symbolList

@ExperimentalAnimationApi
@Composable
fun BodyField(
    bodyChange: (String) -> Unit,
    body: String,
) {
    var isFocused by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(body)) }

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
            initiallyVisible = false,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .padding(end = 6.dp)
                    .animateContentSize(),
            ) {
                val symbols = symbolList
                val placeSymbol = { symbol: String ->
                    textFieldValue = TextFieldValue("${textFieldValue.text}$symbol")
                    focusRequester.requestFocus()
                    textFieldValue = textFieldValue.copyWithCursorPosition(CursorPosition.End)
                }
                Spacer(modifier = Modifier.padding(bottom = 6.dp))
                symbols.forEach { symbol ->
                    Button(
                        modifier = Modifier.size(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colors.background
                        ),
                        onClick = { placeSymbol(symbol) },
                    ) {
                        Text(symbol)
                    }
                    Spacer(modifier = Modifier.padding(bottom = 6.dp))
                }
                Spacer(modifier = Modifier.padding(bottom = 70.dp))

            }
        }
    }
}
