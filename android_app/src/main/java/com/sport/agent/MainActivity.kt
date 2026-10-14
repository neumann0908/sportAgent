package com.sport.agent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sport.agent.ui.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

private val DarkSportColorScheme = darkColorScheme(
    primary = Color(0xFF22C55E),      // Verde principal brillante
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF4ADE80),    // Verde secundario
    background = Color(0xFF121212),   // Fondo oscuro general
    surface = Color(0xFF1E1E1E),      // Superficies (tarjetas, contenedores)
    onBackground = Color(0xFFE2E8F0),
    onSurface = Color(0xFFE2E8F0)
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = DarkSportColorScheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
