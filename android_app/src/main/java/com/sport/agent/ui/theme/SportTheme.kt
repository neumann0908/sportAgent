package com.sport.agent.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SportColorScheme = darkColorScheme(
    primary = SportGreen,
    onPrimary = SportBackground,
    secondary = SportGreenLight,
    onSecondary = SportBackground,
    tertiary = SportBlue,
    background = SportBackground,
    onBackground = SportText,
    surface = SportSurface,
    onSurface = SportText,
    surfaceVariant = SportCard,
    onSurfaceVariant = SportTextSecondary,
    error = SportRed,
    onError = SportBackground
)

@Composable
fun SportTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SportColorScheme,
        typography = SportTypography,
        content = content
    )
}
