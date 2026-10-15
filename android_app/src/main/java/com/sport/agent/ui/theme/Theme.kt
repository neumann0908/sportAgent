package com.sport.agent.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = SportGreen,
    surface = SportCard,
    background = SportBackground,
    onPrimary = SportBackground,
    onSurface = SportTextPrimary,
    onBackground = SportTextPrimary
)

@Composable
fun SportAgentTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
