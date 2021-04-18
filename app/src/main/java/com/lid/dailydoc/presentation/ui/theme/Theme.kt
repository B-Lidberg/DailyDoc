package com.lid.dailydoc.presentation.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = GrayDark,
    primaryVariant = GrayLight,
    onPrimary = Color.Black,
    secondary = GrayLight,
    secondaryVariant = GrayLight,
    onSecondary = Color.Black,
    background = GrayBackground,
    onBackground = Color.White,
    surface = GrayBackground,
    onSurface = Color.White,
    error = Red300,
    onError = Color.Black
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Blue500,
    primaryVariant = Blue400,
    onPrimary = Color.White,
    secondary = Aqua,
    secondaryVariant = Blue500,
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Red800,
    onError = Color.White

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DailyDocTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}