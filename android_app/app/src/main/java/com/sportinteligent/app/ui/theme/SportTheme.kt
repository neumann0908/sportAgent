package com.sportinteligent.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    background = BackgroundPrimary,
    surface = BackgroundSecondary,
    error = RedError,
    onPrimary = BackgroundPrimary,
    onSecondary = BackgroundPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
)

@Composable
fun SportInteligentTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
