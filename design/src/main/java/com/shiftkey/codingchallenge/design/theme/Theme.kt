package com.shiftkey.codingchallenge.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = Green,
    primaryVariant = LightGreen,
    secondary = Green,
    background = Gray249,
    surface = White,
    onBackground = Gray197,
)

@Composable
fun ShiftKeyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        LightColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = ShiftsTypography,
        content = content
    )
}
