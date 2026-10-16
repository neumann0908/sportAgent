package com.sport.agent.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = SportGreen,
    secondary = SportBlue,
    background = SportBackground,
    surface = SportSurface,
    onPrimary = SportText,
    onSecondary = SportText,
    onBackground = SportText,
    onSurface = SportText,
    error = SportRed
)

@Composable
fun SportTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
