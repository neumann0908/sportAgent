package com.sport.agent.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Definiciones de colores locales para garantizar compilación limpia
val SportGreen = Color(0xFF00E676)
val SportGreenLight = Color(0xFF69F0AE)
val SportBlue = Color(0xFF2979FF)
val SportCard = Color(0xFF1E1E1E)
val SportBackground = Color(0xFF121212)
val SportSurface = Color(0xFF121212)
val SportBorder = Color(0xFF2C2C2C)
val SportText = Color(0xFFFFFFFF)
val SportTextPrimary = Color(0xFFFFFFFF)
val SportTextSecondary = Color(0xFFB0BEC5)
val SportYellow = Color(0xFFFFD54F)
val SportRed = Color(0xFFFF5252)

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
