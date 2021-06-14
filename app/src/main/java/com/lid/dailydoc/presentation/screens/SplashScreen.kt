package com.lid.dailydoc.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay


private const val SplashWaitTime: Long = 1500

@Composable
fun SplashScreen(
    toMain: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Daily Doc", style = MaterialTheme.typography.h3)

        LaunchedEffect(Unit) {
            delay(SplashWaitTime)
            toMain()
        }
    }
}