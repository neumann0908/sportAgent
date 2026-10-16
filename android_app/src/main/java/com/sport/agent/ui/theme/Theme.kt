package com.sport.agent.ui.theme

import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    SportTheme(darkTheme = darkTheme, content = content)
}
